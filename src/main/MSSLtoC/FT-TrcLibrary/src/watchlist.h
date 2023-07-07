typedef struct watch_list_t *watch_list_t;
typedef struct watch_cell_t *watch_cell_t;

watch_list_t  _create_watch_list      (void);
int           _get_length_watch_list  (watch_list_t list);
int            _add_watch_list       (watch_list_t,ft_event_t, char func[20]);
int            _append_watch_list     (watch_list_t list,ft_event_t event, char func[20]);
void           _reset_watch_list       (watch_list_t);
void           _reset_event_in_list    (ft_event_t);
int           _apply_watch_list_watch   (watch_list_t list, ft_thread_t thread);
void          _reset_last_event_in_list_watch (void);
int          _reset_event_in_list_watch      (void);
int            _get_length_watch_list  (watch_list_t);
watch_cell_t  _get_first_watch_list   (watch_list_t);
ft_event_t    _get_content_watch_cell (watch_cell_t);
watch_cell_t  _get_next_watch_cell    (watch_cell_t);


