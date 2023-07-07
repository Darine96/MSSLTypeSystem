typedef struct event_list_t *event_list_t;
typedef struct event_cell_t *event_cell_t;

typedef struct watch_list_t *watch_list_t;
typedef struct watch_cell_t *watch_cell_t;

event_list_t  _create_event_list      (void);
int            _add_event_list         (event_list_t,ft_event_t);
void           _reset_event_list       (event_list_t);
void           _reset_event_in_list    (ft_event_t);
void           _apply_event_list        (event_list_t,ft_event_t);
int           _apply_event_list_watch   (event_list_t list, ft_thread_t thread);
//void          _reset_last_event_in_list_watch (void);
//void          _reset_event_in_list_watch      (void);
void           _apply_event_list_for_cooperateCaseWhen  (event_list_t);
void           ft_thread_when_event(ft_event_t);
int            _get_length_event_list  (event_list_t);
event_cell_t  _get_first_event_list   (event_list_t);
ft_event_t    _get_content_event_cell (event_cell_t);
event_cell_t  _get_next_event_cell    (event_cell_t);

