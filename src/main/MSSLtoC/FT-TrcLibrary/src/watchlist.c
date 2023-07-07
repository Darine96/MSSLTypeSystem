#include "fthread_internal.h"
#include <stdbool.h> 
#include <string.h>


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
int _get_length_watch_list  (watch_list_t list)
{
   return list->length;
}

watch_cell_t _get_first_watch_list (watch_list_t list)
{
   return list->first;
}

ft_event_t _get_content_watch_cell (watch_cell_t cell)
{
   return cell->content;
}

watch_cell_t _get_next_watch_cell (watch_cell_t cell)
{
   return cell->next;
}
/***********************************************************/
// creation
watch_list_t _create_watch_list (void)
{
   watch_list_t res = malloc (sizeof (struct watch_list_t));
   res->length = 0;
   res->first = NULL;
   res->last = NULL;
   return res;
}


// add a new event as last element
int _append_watch_list (watch_list_t list,ft_event_t event, char func[20])
{
   watch_cell_t new = malloc (sizeof (struct watch_cell_t));
   if (NULL == new) return EBADMEM;
   new->content = event;
   strcpy(new->func, func);
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

//reset last event in list watch cases
void _reset_last_event_in_list_watch (void)
{
    watch_list_t list = ft_thread_list_watch();
   if (list->length != 0) {
      if(list->length == 1)
      {
         list->length = 0;
         list->first = NULL;
         list->last = NULL;
         return;

      }
      watch_cell_t cell = list->first;
      while (cell != NULL) {
   watch_cell_t next = cell->next;
   cell = next;
      }
               free(cell);
               cell= NULL;
               //list->last = NULL;
               list->length--;
               return ;

   }
}


//reset events in list watch cases
int _reset_event_in_list_watch (void)
{   bool check_generate = false;
   int i = 0;

    watch_list_t list = ft_thread_list_watch();
    ft_event_t ev = ft_thread_event_on_watch();

   if (list->length != 0) {
      watch_cell_t cell = list->first;
      while (cell != NULL) {
         i++;
   watch_cell_t next = cell->next;
  
   if (cell->content == ev)

            { 
               check_generate = true;
               
            }
   if(check_generate)
            {
               free(cell);
               list->length--;
               return (i-1);
            }
   cell = next;
      }
   }
   return -1;
}
 


// reset the list
void _reset_watch_list (watch_list_t list)
{
   if (list->length != 0) {
      watch_cell_t cell = list->first;
      while (cell != NULL) {
   watch_cell_t next = cell->next;
   free (cell);
   cell = next;
      }
   }
   list->length = 0;
   list->first = NULL;
   list->last = NULL;
}
// is a event contained in the list ?
int _contains_watch_list (watch_list_t list,ft_event_t event)
{
   if (list->length != 0) {
      watch_cell_t cell = list->first;
      while (cell != NULL) {
   watch_cell_t next = cell->next;
         if (cell->content == event) 
            {  fprintf (stdout,"egaux\n");
               return 1;
            }
   cell = next;
      }
   }
   return 0;
}
// add a new event if not already present in the list
int _add_watch_list (watch_list_t list,ft_event_t event, char func[20])
{

   if (_contains_watch_list (list,event)) {
      printf("helloooooooooooooooooooooooo\n");
      return OK;}
   return _append_watch_list (list,event,func);
}

/*** Case for watch ***/

int _apply_watch_list_watch (watch_list_t list, ft_thread_t thread)// this function take a list of events and a event
{  int i=0;
   if (list->length == 0) return 0;
   watch_cell_t cell = list->first;
   ft_event_t event = cell->content;
  while (cell != NULL) {
   i++;
   //check if the event is generate
   ft_event_t watch = cell->content;
   if(_event_is_generated(watch)){
      _reset_event_watch(thread);
      _set_thread_event_watch(thread, watch);
      _set_thread_name_function(thread, cell->func);
      //printf("the name's function of event watch case is %s\n", cell->func);
      return 1;
   }
  // fprintf (stdout,"lengthffffffffff %d\n", list->length);
   watch_cell_t next = cell->next;
   cell = next;
   if(watch == event && i!=1){
      //fprintf (stdout,"lengthffffffffff %d\n", i);
      break;
      } 
   }
   return 0;
}

/********************************************/

