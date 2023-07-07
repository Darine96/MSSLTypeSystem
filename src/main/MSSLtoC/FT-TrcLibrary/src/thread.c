#include "fthread_internal.h"
#include "string.h"
/******************************************/
struct ft_thread_t
{
   pthread_t                 pthread;      // underlying pthread
   int                       well_created; // equals FT_MAGIC if creation is OK

   pthread_mutex_t           lock;
   pthread_cond_t            token;
   int                       has_token;

   event_list_t             event_list;    //apply the idea of when
   watch_list_t               watch;         //apply the watch idea
   ft_event_t                content;         // event watch generate 
   int                     case_watch;     //verify
   char                       func[20];    //name_function
   int                       taille;       // taille array in function
   int                     case_func;      //case event watch not in current function

   ft_executable_t           cleanup;      // cleanup function, run when stopped   
   ft_executable_t           run;          // the thread function
   void                     *args;         // parameters for run and cleanup

   ft_scheduler_t            scheduler;    // scheduler of the thread

   int                       status;       // status of the thread
   int                       stopped;      // set when stopped

   lock_list_t               locks;        // list of locks owned by the thread

   int                       deadline;     // limit time when waiting

   int                       is_automaton; // is it an automaton ?
   ft_automaton_t            automaton;    // automaton function
   int                       state;        // automaton state
   void                     *local;        // local data
   int                       return_code;  // return code for instructions run by the automaton
};

/******************************************/
ft_scheduler_t _get_thread_scheduler (ft_thread_t thread)
{
   return thread->scheduler;
}

void _set_thread_scheduler (ft_thread_t thread,ft_scheduler_t sched)
{
   thread->scheduler = sched;
}

int _get_thread_status (ft_thread_t thread)
{
   return thread->status;
}

void _set_thread_status (ft_thread_t thread,int status)
{
   thread->status = status;
}

int _get_thread_deadline (ft_thread_t thread)
{
   return thread->deadline;
}
void _set_thread_deadline (ft_thread_t thread,int deadline)
{
   thread->deadline = deadline;
}

int _is_automaton (ft_thread_t thread)
{
   return thread->is_automaton;
}
int _get_thread_state (ft_thread_t thread)
{
   return thread->state;
}

void _set_thread_state (ft_thread_t thread,int state)
{
   thread->state = state;
}

void* _get_thread_args (ft_thread_t thread)
{
   return thread->args;
}

void* _get_thread_local (ft_thread_t thread)
{
   return thread->local;
}
void _set_thread_local (ft_thread_t thread,void *data)
{
   thread->local = data;
}

int _get_thread_return_code (ft_thread_t thread)
{
   return thread->return_code;
}
void _set_thread_return_code (ft_thread_t thread,int code)
{
   thread->return_code = code;
}

/*****************************************************/
void _add_lock (ft_thread_t thread,pthread_mutex_t *mutex)
{
   thread->locks = _add_to_lock_list (mutex,thread->locks);
}
void _remove_lock (ft_thread_t thread,pthread_mutex_t *mutex)
{
   thread->locks = _remove_from_lock_list (mutex,thread->locks);
}

/*****************************************************/
void _stop_thread (ft_thread_t thread)
{
  thread->stopped = 1;
}

/*****************************************************/
int _thread_well_created (ft_thread_t thread)
{
   return  thread->well_created == FT_MAGIC;
}

/************************************************/
/************************************************/
void _lock_thread (ft_thread_t thread)
{
   _PTH_LOCK(thread->lock);
}

void _unlock_thread (ft_thread_t thread)
{
   _PTH_UNLOCK(thread->lock);
}

void _wait_thread (ft_thread_t thread)
{
   _PTH_WAIT(thread->token,thread->lock);
}

void _notify_thread (ft_thread_t thread)
{
   _PTH_NOTIFY(thread->token,thread->lock);
}

static void _wait_for_token (ft_thread_t thread){
   _lock_thread (thread);   
   while (thread->has_token == 0) _wait_thread (thread);
   thread->has_token = 0;
   _unlock_thread (thread);   
}

void _give_token_to (ft_thread_t thread){
   _lock_thread (thread);
   thread->has_token = 1;
   _notify_thread (thread);   
   _unlock_thread (thread);   
}

void _transmit_token (ft_thread_t source,ft_thread_t target){
   _give_token_to (target);
   _wait_for_token (source);
}

/*****************************************************/
static pthread_once_t _once_self_key = PTHREAD_ONCE_INIT;
static pthread_key_t _self_key;

static void _init_self_key (void)
{
   pthread_key_create (&_self_key,NULL);
}

/*****************************************************/
void ft_thread_reset_event(ft_event_t event){
	_reset_event_in_list(event);
}

/*****************************************************/
ft_thread_t ft_thread_self (void) // no sense with automata
{
   return pthread_getspecific (_self_key);
}

ft_scheduler_t ft_thread_scheduler (void) // no sense with automata
{
   ft_thread_t res = ft_thread_self ();
   if (res == NULL) return NULL;
   return res->scheduler;
}


event_list_t ft_thread_list_event(void){
   ft_thread_t res = ft_thread_self ();
   if (res == NULL) return NULL;
   return res->event_list;
}

//for scheduler
watch_list_t ft_thread_list_watch_event(ft_thread_t res){
   //ft_thread_t res = ft_thread_self ();
   if (res == NULL) return NULL;
   return res->watch;
}


ft_event_t ft_thread_event_on_watch(void){
   ft_thread_t res = ft_thread_self ();
   if (res == NULL) return NULL;
   return res->content;
}

watch_list_t ft_thread_list_watch(void){
   ft_thread_t res = ft_thread_self ();
   if (res == NULL) return NULL;
   return res->watch;
}

int _reset_event_watch(ft_thread_t thread){
   //ft_thread_t res = ft_thread_self ();
   if (thread == NULL) return 0;
   if(thread->content!=NULL) {thread->content=NULL;/*free(res->watch);*/}
   return 1;
}

void _set_thread_event_watch(ft_thread_t thread, ft_event_t event){
    //ft_thread_t res = ft_thread_self ();
   if (thread == NULL) return;
   thread->content = event;
}
void _set_thread_case_watch(ft_thread_t thread, int c){
    //ft_thread_t res = ft_thread_self ();
   //if (res == NULL) return;
   thread->case_watch = c;
}
int _get_thread_case_watch(ft_thread_t thread){
   // ft_thread_t res = ft_thread_self ();
   //if (res == NULL) return;
   return thread->case_watch;
}

void _set_thread_case_func(int c){
    ft_thread_t res = ft_thread_self ();
   if (res == NULL) return;
   res->case_func = c;
}
int _get_thread_case_func(void){
    ft_thread_t res = ft_thread_self ();
   if (res == NULL) return -1;
   return res->case_func;
}

ft_event_t ft_thread_event_watch(ft_thread_t thread){
   return thread->content;
}

int _get_thread_taille(void){
    ft_thread_t res = ft_thread_self ();
   if (res == NULL) return -1;
   return res->taille;
}

void _set_thread_taille(int taille){
   ft_thread_t res = ft_thread_self ();
   if (res == NULL) return;
   int t = res->taille + taille;
   res->taille = t;
}

void _set_thread_taille_initialise(void){
   ft_thread_t res = ft_thread_self ();
   if (res == NULL) return;
   res->taille = 0;
}

int _compare_function_name(char func[20]){
    ft_thread_t res = ft_thread_self ();
   if (res == NULL) return -1;
   if(strcmp(func, res->func) == 0){
      
      return 1;
   }
   else {
      return 0;
   }
}

void _set_thread_name_function(ft_thread_t thread, char func[20]){
    //ft_thread_t res = ft_thread_self ();
   //if (res == NULL) return;
   //res->func[20] = func;
   strcpy(thread->func, func);
}
/************************************************/
/************************************************/
void _terminate (ft_thread_t thread)
{
   thread->status = _TERMINATED;
   _unlock_all (thread->locks);
   if (thread->is_automaton) return;

   if (thread->scheduler != NULL) {
      _give_token_to (_get_scheduler_self (thread->scheduler));
   }
   pthread_exit (0); //never pass through
}

/*****************************************************/
/*atomically: register in the scheduler, notify the creation function,
              and wait the token from the scheduler */

int _start_phase (ft_thread_t thread)
{
   int res;

   _lock_thread (thread);
   
   res = _register_as_runnable (thread);
   _notify_thread (thread);

   if (res == OK){
      while (thread->has_token == 0) _wait_thread (thread);
      thread->has_token = 0;
   }

   _unlock_thread (thread);
   
   return res;
}

static void* _standard_behavior (void *param)
{
   /*the current thread is the parameter */
   ft_thread_t me = param;

   pthread_setspecific (_self_key,me);
  
   if (_start_phase (me) != OK) return NULL; // cannot register => nothing done
  
   me->run (me->args);
   _terminate (me);         // normal termination

   return NULL;
}

/*****************************************************/
ft_thread_t _make_thread (void)
{
   pthread_mutex_t lock;
   pthread_cond_t token;

   ft_thread_t thread = malloc (sizeof (struct ft_thread_t));
   if (thread == NULL) return NULL;

   pthread_mutex_init (&lock,NULL);
   pthread_cond_init  (&token,NULL);

   thread->pthread          = 0;
   thread->lock             = lock;
   thread->token            = token;
   
   thread->cleanup          = NULL;
   thread->run              = NULL;
   thread->args             = NULL;
  
   thread->scheduler        = NULL;

   thread->status           = _READY;
   thread->event_list       = _create_event_list ();
   thread->content          = NULL;
   thread->watch            =_create_watch_list ();
   thread->case_watch       =0;
   strcpy(thread->func, "");
   thread->taille           = 0;
   thread->case_func        =0;
   thread->has_token        = 0;

   thread->stopped          = 0;

   thread->locks            = NULL;

   thread->deadline         = -1;
   thread->is_automaton     = 0;
   thread->automaton        = NULL;
   thread->state            = 0;
   thread->local            = NULL;   
   thread->return_code      = 0;
   
   return thread;
}

ft_thread_t ft_thread_create (ft_scheduler_t sched,
			      ft_executable_t runnable,
			      ft_executable_t cleanup,
			      void *args)
{
   int res;
   ft_thread_t thread;

   if (sched == NULL || !_scheduler_well_created (sched)) return NULL;

   pthread_once (&_once_self_key,_init_self_key);

   if (NULL == (thread = _make_thread ())) return NULL;
  
   thread->run          = runnable;
   thread->cleanup      = cleanup;
   thread->args         = args;
   thread->scheduler    = sched; // scheduler is set

   // create the pthread and wait until it is running
   _lock_thread (thread);

   res = pthread_create (&thread->pthread,NULL,_standard_behavior,thread);
   if (res == OK) thread->well_created = FT_MAGIC;
   _wait_thread (thread);
   _unlock_thread (thread);
  
   return thread;
}

/************************************************/
pthread_t ft_pthread (ft_thread_t thread)
{
   if (thread == NULL) return 0;
   return thread->pthread;
}

/*****************************************************/
void _release (ft_thread_t thread,int status)
{
   ft_scheduler_t sched = _get_thread_scheduler (thread);
   _set_thread_status (thread,status);
   _transmit_token (thread,_get_scheduler_self(sched));   

   if (thread->stopped) {
      if (thread->cleanup != NULL) thread->cleanup (thread->args);
      _terminate (thread);
   }
}

/************************************************/
void _trace_thread (ft_thread_t thread)
{
   if (thread->status == _WAIT) fprintf (stderr, "%ld* ",(long)thread);
   else fprintf (stderr, "%ld ",(long)thread);
}

/************************************************/
void ft_exit (void)
{
   ft_thread_t me = ft_thread_self ();
   if (me != NULL) {
      _terminate (me);
   } else pthread_exit (NULL);
}

/************************************************/
void ft_free(ft_thread_t thread){
   _reset_event_list (thread->event_list);
   free(thread->event_list);
   _reset_watch_list (thread->watch);
   free(thread->watch);
   free(thread);
}



/*****************************************************/
/*****************************************************/
ft_thread_t ft_automaton_create (ft_scheduler_t sched,
				 ft_automaton_t automaton,
				 ft_executable_t cleanup,
				 void *args)
{
   ft_thread_t thread;

   if (sched == NULL || !_scheduler_well_created (sched)) return NULL;

   pthread_once (&_once_self_key,_init_self_key);
   
   if (NULL == (thread = _make_thread ())) return NULL;

   thread->cleanup      = cleanup;
   thread->args         = args;
   thread->scheduler    = sched; // scheduler is set
   
   thread->is_automaton = 1;
   thread->automaton    = automaton;

   thread->well_created = FT_MAGIC; // before registrating...
       
   if (OK != _register_as_runnable (thread)) return NULL;
   return thread;
}

void _run_as_automaton (ft_thread_t thread)
{
   if (thread->stopped) {
      if (thread->cleanup != NULL) thread->cleanup (thread->args);
      _terminate (thread);
   } else {
      pthread_setspecific (_self_key,thread); 
      thread->automaton (thread);
   }
}
