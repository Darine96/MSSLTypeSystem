#include "fthread_internal.h"
#include <stdbool.h> 
struct event_cell_t
{
  ft_event_t            content;
  struct event_cell_t  *next;
};

struct event_list_t
{
  int            length;
  event_cell_t  first;
  event_cell_t  last;
};




struct watch_cell_t
{
  ft_event_t            content;
  char                  func[20];
  struct watch_cell_t  *next;
};

struct watch_list_t
{
  int            length;
  watch_cell_t  first;
  watch_cell_t  last;
};
/***********************************************************/
int _get_length_event_list  (event_list_t list)
{
   return list->length;
}

event_cell_t _get_first_event_list (event_list_t list)
{
   return list->first;
}

ft_event_t _get_content_event_cell (event_cell_t cell)
{
   return cell->content;
}

event_cell_t _get_next_event_cell (event_cell_t cell)
{
   return cell->next;
}
/***********************************************************/
// creation
event_list_t _create_event_list (void)
{
   event_list_t res = malloc (sizeof (struct event_list_t));
   res->length = 0;
   res->first = NULL;
   res->last = NULL;
   return res;
}

// add a new event as last element
int _append_event_list (event_list_t list,ft_event_t event)
{
   event_cell_t new = malloc (sizeof (struct event_cell_t));
   if (NULL == new) return EBADMEM;
   new->content = event;
   new->next = NULL;
   
   if (0 == list->length){
      list->first = list->last = new;
   }else{
      list->last->next = new;
      list->last = new;
   }

   list->length++;

   return OK;
}
// reset the list
void _reset_event_in_list (ft_event_t event)
{
    event_list_t list = ft_thread_list_event();
   if (list->length != 0) {
      event_cell_t cell = list->first;
      while (cell != NULL) {
   event_cell_t next = cell->next;
   if (cell->content == event)

            {  
               free(cell);
               list->length--;
               return ;
            }
   cell = next;
      }
   }
}

//reset last event in list watch cases
/*void _reset_last_event_in_list_watch (void)
{
    event_list_t list = ft_thread_list_watch();
   if (list->length != 0) {
      if(list->length == 1)
      {
         list->length = 0;
         list->first = NULL;
         list->last = NULL;

      }
      event_cell_t cell = list->first;
      while (cell != NULL) {
   event_cell_t next = cell->next;
   if (next == NULL)

            {  printf("free done\n");
               free(cell);
               list->length--;
               return ;
            }
   cell = next;
      }
   }
}


//reset events in list watch cases
void _reset_event_in_list_watch (void)
{   bool check_generate = false;
    event_list_t list = ft_thread_list_watch();
    ft_event_t ev = ft_thread_event_on_watch();
   if (list->length != 0) {
      event_cell_t cell = list->first;
      while (cell != NULL) {
   event_cell_t next = cell->next;
  
   if (cell->content == ev)

            { 
               check_generate = true;
               
            }
   if(check_generate)
            {
               free(cell);
               list->length--;
               return ;
            }
   cell = next;
      }
   }
}*/
 


// reset the list
void _reset_event_list (event_list_t list)
{
   if (list->length != 0) {
      event_cell_t cell = list->first;
      while (cell != NULL) {
   event_cell_t next = cell->next;
   free (cell);
   cell = next;
      }
   }
   list->length = 0;
   list->first = NULL;
   list->last = NULL;
}
// is a event contained in the list ?
int _contains_event_list (event_list_t list,ft_event_t event)
{
   if (list->length != 0) {
      event_cell_t cell = list->first;
      while (cell != NULL) {
   event_cell_t next = cell->next;
         if (cell->content == event) 
            { // fprintf (stdout,"egaux\n");
               return 1;
            }
   cell = next;
      }
   }
   return 0;
}
// add a new event if not already present in the list
int _add_event_list (event_list_t list,ft_event_t event)
{

   if (_contains_event_list (list,event)) {
     // printf("helloooooooooooooooooooooooo\n");
      return OK;}
   return _append_event_list (list,event);
}

// apply a function f to all threads in the list.
// f(thread) returns 1 = remove the thread, 0 keep it
void _apply_event_list (event_list_t list, ft_event_t event)// this function take a list of events and a event
{  //printf("\n %d \n", _get_length_event_list (list));
   if (list->length == 0) return;
   event_cell_t cell = list->first;
   ft_thread_await(event);
   // printf("\n %d \n", _get_length_event_list (list));
  while (cell != NULL) {
   event_cell_t next = cell->next;
   //check if the event is generate
   ft_event_t ev = cell->content;
         ft_thread_await(ev);

   cell = next;
   if(ev == event){
      break;
      } 
   }
}

/*** Case for watch ***/

int _apply_event_list_watch (event_list_t list, ft_thread_t thread)// this function take a list of events and a event
{  int i=0;
   if (list->length == 0) return 0;
   event_cell_t cell = list->first;
   ft_event_t event = cell->content;
  while (cell != NULL) {
   i++;
   //check if the event is generate
   ft_event_t watch = cell->content;
   if(_event_is_generated(watch)){
      _reset_event_watch(thread);
      _set_thread_event_watch(thread, watch);
      return 1;
   }
  // fprintf (stdout,"lengthffffffffff %d\n", list->length);
   event_cell_t next = cell->next;
   cell = next;
   if(watch == event && i!=1){
      //fprintf (stdout,"lengthffffffffff %d\n", i);
      break;
      } 
   }
   return 0;
}

/**** Cooperate in When ****/

void _apply_event_list_for_cooperateCaseWhen (event_list_t list)// this function take a list of events and a event
{  int i=0;
   if (list->length == 0) return;
   event_cell_t cell = list->first;
   ft_event_t event = cell->content;
   //fprintf (stdout,"length %d\n", list->length);
  while (cell != NULL) {
   i++;
   //check if the event is generate
   ft_event_t ev = cell->content;
   ft_thread_await(ev);
   event_cell_t next = cell->next;
   cell = next;
   if(ev == event && i!=1){
      //fprintf (stdout,"lengthffffffffff %d\n", i);
      break;
      } 
   }
}
/********************************************/
