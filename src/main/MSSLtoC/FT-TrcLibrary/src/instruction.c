#include "fthread_internal.h"
#include "string.h"
/************************************************/
int ft_thread_cooperate ()
{
   //printf("Hello World");
   ft_thread_t self  = ft_thread_self ();

   _VERIFY_THREAD_CREATION_AND_LINK (self);

   _release(self,_DONE);
      // verify if all events in list_event are emitted
   event_list_t list = ft_thread_list_event();
   _apply_event_list_for_cooperateCaseWhen(list);
   return OK;
} 

int ft_thread_cooperate_n (int delay)
{ 
   int deadline;
   ft_thread_t    self  = ft_thread_self ();
   ft_scheduler_t sched = ft_thread_scheduler ();

   _VERIFY_THREAD_CREATION_AND_LINK (self);

   deadline = delay + _get_scheduler_instant (sched);
   
   while (1) {
      if (deadline <= _get_scheduler_instant (sched)) return OK;
      _POST_TIMER (self,sched,deadline);
      _release (self,_WAIT);      
   }
}

/************************************************/
int ft_thread_await (ft_event_t event)
{   
   ft_thread_t    self  = ft_thread_self ();
   ft_scheduler_t sched = ft_thread_scheduler ();

   _VERIFY_THREAD_CREATION_AND_LINK (self);
   _VERIFY_EVENT_CREATION_AND_SCHEDULER (event,sched);
 //  ft_event_t watch = ft_thread_event_watch(self); 
   while (1) {
      
      if (_event_is_generated(event)) return OK;               // present
      else if (_get_scheduler_eoi (sched)) _release (self,_DONE);  // absent
      else if(_get_thread_case_watch(self) && _get_thread_status (self) == _READY){
            return OK;
      }// watch case
      else {
         
	 _POST_EVENT (event,self);
	 _release (self,_WAIT);

      }
   } 
}

int ft_thread_await_n (ft_event_t event,int delay)
{ 
   int deadline;
   ft_thread_t    self  = ft_thread_self ();
   ft_scheduler_t sched = ft_thread_scheduler ();

   _VERIFY_THREAD_CREATION_AND_LINK (self);
   _VERIFY_EVENT_CREATION_AND_SCHEDULER (event,sched);
   
   deadline = delay + _get_scheduler_instant (sched);
  
   while(1) {
      _CONTROL_TIMEOUT (deadline,sched);
      if (_event_is_generated (event)) return OK;
      else if (_get_scheduler_eoi (sched)) _release (self,_DONE);
      else {
	 _POST_TIMER (self,sched,deadline);	 
	 _POST_EVENT (event,self);
	 _release (self,_WAIT);
      }
   }
}

/************************************************/
int ft_thread_join (ft_thread_t thread)
{
   ft_thread_t    self   = ft_thread_self ();
   ft_scheduler_t sched  = ft_thread_scheduler ();
  
   _VERIFY_THREAD_CREATION_AND_LINK (self);
   _VERIFY_THREAD_CREATION (thread);

   while (1) {
      if (_get_thread_status (thread) == _TERMINATED) {
	 /* if termination comes from an asynchronous thread
	    and somebody also waits for termination, one must
	    be sure that it sees termination at the same instant. */
	 _set_scheduler_move (sched); 
	 return OK;
      } else if (_get_scheduler_eoi (sched)) _release (self,_DONE);
      else _release (self,_BLOCKED);
   }
}

int ft_thread_join_n (ft_thread_t thread,int delay)
{
   int deadline;
   ft_thread_t    self   = ft_thread_self ();
   ft_scheduler_t sched  = ft_thread_scheduler ();
  
   _VERIFY_THREAD_CREATION_AND_LINK (self);
   _VERIFY_THREAD_CREATION (thread);
  
   deadline = delay + _get_scheduler_instant (sched);
   
   while(1) {
      _CONTROL_TIMEOUT (deadline,sched);
      if (_get_thread_status (thread) == _TERMINATED) {
	 _set_scheduler_move (sched); 
	 return OK;
      } else if (_get_scheduler_eoi (sched) == 1) {
	 delay--;
	 _release (self,_DONE);
      } else _release (self,_BLOCKED);
   }
}

/************************************************/
int ft_thread_get_value (ft_event_t event,int index,void **result)
{
   ft_thread_t    self  = ft_thread_self ();
   ft_scheduler_t sched = ft_thread_scheduler ();

   _VERIFY_THREAD_CREATION_AND_LINK (self);
   _VERIFY_EVENT_CREATION_AND_SCHEDULER (event,sched);

   while(1) {
      if (_event_get_value (event,index,result)) return OK;
      if (_get_scheduler_eoi (sched) == 1) break;
      _release (self,_BLOCKED);
   }
   _release (self,_DONE);
   result = NULL;
   return ENEXT;
}

/************************************************/
int ft_thread_generate (ft_event_t event)
{
   ft_thread_t    self  = ft_thread_self ();
   ft_scheduler_t sched = ft_thread_scheduler ();

   _VERIFY_THREAD_CREATION_AND_LINK (self);
   _VERIFY_EVENT_CREATION_AND_SCHEDULER (event,sched);
   
   _event_generate (event);
   return OK;
}

int ft_thread_generate_value (ft_event_t event,void* val)
{
   ft_thread_t    self  = ft_thread_self ();
   ft_scheduler_t sched = ft_thread_scheduler ();

   _VERIFY_THREAD_CREATION_AND_LINK (self);
   _VERIFY_EVENT_CREATION_AND_SCHEDULER (event,sched);

   return _event_generate_value (event,val);
}

/************************************************/
int ft_thread_mutex_lock (pthread_mutex_t *mutex)
{
   ft_thread_t    self  = ft_thread_self ();
   ft_scheduler_t sched = ft_thread_scheduler ();
   
   if (self == NULL || sched == NULL) return pthread_mutex_lock(mutex);

   _add_lock (self,mutex);
   
   while(1){
     if (_get_scheduler_eoi (sched) == 1) {
	_release (self,_DONE);
     }else if (pthread_mutex_trylock (mutex) == OK) {
	return OK;
     }else{
	_release (self,_BLOCKED);
     }
   }
}

int ft_thread_mutex_unlock (pthread_mutex_t *mutex)
{
   ft_thread_t self = ft_thread_self ();
   
   int res = pthread_mutex_unlock (mutex);
   
   if (self != NULL) {
      ft_scheduler_t sched = _get_thread_scheduler (self);
      if (sched != NULL){
	 _set_scheduler_move (sched);
         _remove_lock (self,mutex);
      }
   }
   return res;
}

/***********************************************/
int ft_thread_select (int length,ft_event_t *events,int *mask)
{
   int i;
   ft_thread_t    self  = ft_thread_self ();
   ft_scheduler_t sched = ft_thread_scheduler ();

   _VERIFY_THREAD_CREATION_AND_LINK (self);

   for (i=0;i<length;i++) {
      _VERIFY_EVENT_CREATION_AND_SCHEDULER (events[i],sched);      
   }

   while (1) {
      if (_fill_mask (length,events,mask)) return OK;
      else if (_get_scheduler_eoi (sched)) _release (self,_DONE);
      else {
	 for (i=0;i<length;i++) _POST_EVENT (events[i],self);
	 _release (self,_WAIT);
      }
   } 
}

int ft_thread_select_n (int length,ft_event_t *events,int *mask,int delay)
{
   int deadline,i;
   ft_thread_t    self  = ft_thread_self ();
   ft_scheduler_t sched = ft_thread_scheduler ();

   _VERIFY_THREAD_CREATION_AND_LINK (self);

   for (i=0;i<length;i++) {
      _VERIFY_EVENT_CREATION_AND_SCHEDULER (events[i],sched);
   }

   deadline = delay + _get_scheduler_instant (sched);
   
   while (1) {
      _CONTROL_TIMEOUT (deadline,sched);
      if (_fill_mask (length,events,mask)) return OK;
      else if (_get_scheduler_eoi (sched)) _release (self,_DONE);
      else {
	 for (i=0;i<length;i++) _POST_EVENT (events[i],self);
	 _POST_TIMER (self,sched,deadline);	 
	 _release (self,_WAIT);
      }
   } 
}

/********************************************/
int ft_thread_unlink (void)
{
   int res;
   ft_thread_t self = ft_thread_self ();

   _VERIFY_THREAD_CREATION_AND_LINK (self);

   res = _register_unlink_order (self);
   if (res != OK) return res;

   return ft_thread_cooperate ();
}

int ft_thread_link (ft_scheduler_t sched)
{
   ft_thread_t self  = ft_thread_self ();

  _VERIFY_THREAD_CREATION (self);
  _VERIFY_THREAD_UNLINKING (self);
  _VERIFY_SCHEDULER_CREATION (sched);

  _set_thread_scheduler (self,sched);

  return _start_phase (self);
}


void ft_thread_when_event(ft_event_t event)// this function take a list of events and a event
{ 
   /**  add the event into the list **/
   //ft_thread_t self = ft_thread_self ();
   event_list_t list = ft_thread_list_event();
   _add_event_list (list,event);
   
   _apply_event_list(list,event);
}

void ft_thread_reset_when_event(ft_event_t event)
{
   _reset_event_in_list(event);
}

void ft_thread_reset_watch(void)// this function take a list of events and a event
{
   /** reset the watch event into thread after watch block **/
  //_reset_event_watch();

}


int ft_thread_watch_event(ft_event_t event)
{  ft_thread_t self = ft_thread_self ();
   ft_event_t watch = ft_thread_event_watch(self);
   if(_event_is_generated(watch) && !_event_is_generated(event)){
      return 1;
   }

   return 0;

}

void ft_thread_set_event_watch(ft_event_t event, char func[20])
{
   watch_list_t list = ft_thread_list_watch();
   _add_watch_list (list,event,func);
   //_set_thread_event_watch(event);
}

int ft_thread_get_case_watch(void)
{
   ft_thread_t self = ft_thread_self ();
   return _get_thread_case_watch(self);
}

void ft_thread_set_case_watch(void)
{
   ft_thread_t self = ft_thread_self ();
   _set_thread_case_watch(self,0);

}

void ft_thread_watch_done(void)
{
   ft_thread_set_case_watch();
   ft_thread_reset_watch();

}

int ft_thread_watch_list_done(void)
{
   watch_list_t list = ft_thread_list_watch();
   int index = _reset_event_in_list_watch();
  // printf("\n the length of the list %d\n", _get_length_watch_list(list));
   //if(_get_length_event_list(list) == 0){
    //  printf("lsit-> length is NULL\n");
      ft_thread_set_case_watch();
   //}
   return index;
}

void ft_thread_watch_list_not_done(void)
{
   watch_list_t list = ft_thread_list_watch();
   _reset_last_event_in_list_watch();
   //printf(" \n the length of the list %d\n", _get_length_watch_list(list));
   //if(_get_length_event_list(list) == 0){
      //printf("tlsit-> length is NULL\n");
      //ft_thread_set_case_watch();
   //}
   
}

// case watch idea
int ft_thread_watch_case_same_func(void){
  //case 1: function event same current function:
      //get_length_list_watch= length (1)
      watch_list_t list = ft_thread_list_watch();
      int length =  _get_length_watch_list(list);

      //reset_when (2)
      //_reset_event_in_list(event);

      //length - taille(thread) (3)
      int diff = length - _get_thread_taille();

      return diff;
}

int ft_thread_goto(int taille){
   int length = ft_thread_watch_case_same_func();
   //printf("the length is %d\n", length);
   //reset liste_watch en retournant l'indice de l'event (4)
   int indice = ft_thread_watch_list_done();
  // printf("the indice is %d\n", indice);
   return(indice - (length - taille));
}

int ft_thread_compare_function(char func[20]){
   // compare current function avec function event watch
   return _compare_function_name(func);

}

int ft_thread_return_function(int taille){
   // taille = taille+taille(1)
   _set_thread_taille(taille);

   // case_function true (2)
   _set_thread_case_func(1);
   return 1;
}

void ft_thread_case_function_true(void){
   _set_thread_case_func(0);
   _set_thread_taille_initialise();
}

int ft_thread_goto_case_function(int taille){

   //calculer index
   int index = ft_thread_goto(taille);

   //initialiser
   ft_thread_case_function_true();
   return index;
}

int ft_thread_get_case_function(void){
   return _get_thread_case_func();
}


