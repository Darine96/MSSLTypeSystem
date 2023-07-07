#define _READY      0 // ready to run
#define _BLOCKED    2 // waiting to be reexecuted during the same instant
#define _DONE       3 // nothing to be done for current instant
#define _TERMINATED 4 // nothing to be done in the future
#define _SUSPENDED  5 // suspended, waiting to be resumed
#define _WAIT       6 // waiting for an event to be generated
#define _STAY       7 // automaton stays in the same state

int              _thread_well_created       (ft_thread_t);

ft_scheduler_t   _get_thread_scheduler      (ft_thread_t);
void             _set_thread_scheduler      (ft_thread_t,ft_scheduler_t);
int              _get_thread_status         (ft_thread_t);
void             _set_thread_status         (ft_thread_t,int status);
int              _get_thread_deadline       (ft_thread_t);
void             _set_thread_deadline       (ft_thread_t,int deadline);
int              _get_thread_state          (ft_thread_t);
void             _set_thread_state          (ft_thread_t,int state);
void*            _get_thread_args           (ft_thread_t);  
void              ft_free                   (ft_thread_t); 
event_list_t     ft_thread_list_event       (void); 
watch_list_t     ft_thread_list_watch       (void);
watch_list_t     ft_thread_list_watch_event (ft_thread_t); 
ft_event_t       ft_thread_event_on_watch      (void);
int              _reset_event_watch         (ft_thread_t thread);
ft_event_t       ft_thread_event_watch      (ft_thread_t);  
void             _set_thread_event_watch    (ft_thread_t thread, ft_event_t event);
void             _set_thread_name_function  (ft_thread_t, char func[20]);
void             _set_thread_case_watch     (ft_thread_t,int);
int              _get_thread_case_watch     (ft_thread_t);
int              _get_thread_taille         (void);
void             _set_thread_taille         (int taille);
int              _compare_function_name     (char func[20]);
void             _set_thread_case_func      (int c);
int              _get_thread_case_func      (void);
void             _set_thread_taille_initialise (void);
void             ft_thread_reset_event      (ft_event_t event);
 
ft_thread_t      _make_thread               (void);

int              _start_phase               (ft_thread_t);
void             _stop_thread               (ft_thread_t);
void             _lock_thread               (ft_thread_t);
void             _unlock_thread             (ft_thread_t);
void             _trace_thread              (ft_thread_t);

void             _release                   (ft_thread_t,int status);

void             _add_lock                  (ft_thread_t,pthread_mutex_t*);
void             _remove_lock               (ft_thread_t,pthread_mutex_t*);

void             _terminate                 (ft_thread_t);

void             _give_token_to             (ft_thread_t);
void             _transmit_token            (ft_thread_t source,ft_thread_t target);

int              _is_automaton              (ft_thread_t);

void*            _get_thread_local          (ft_thread_t);
void             _set_thread_local          (ft_thread_t,void *data);

int              _get_thread_return_code    (ft_thread_t);
void             _set_thread_return_code    (ft_thread_t,int code);

void             _run_as_automaton          (ft_thread_t);

#define _POST_EVENT(event,self)\
   _event_store_thread (event,self)

#define _POST_TIMER(self,sched,deadline)\
{\
   _set_thread_deadline (self,deadline);\
   _store_in_timer (sched,self);\
}

#define _CONTROL_TIMEOUT(deadline,sched) \
   if (deadline <= _get_scheduler_instant (sched)) return ETIMEOUT

