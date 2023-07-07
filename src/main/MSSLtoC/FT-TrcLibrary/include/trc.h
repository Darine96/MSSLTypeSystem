#ifndef TRC_H
#define TRC_H    

typedef struct Trc *Trc;
typedef struct RefCount *RefCount;

int _get_counter        (Trc trc);

Trc _create_trc         (void * args);
//void * _get_value		(Trc trc);
int _test				(void);			

void _destroy			(Trc trc);


Trc _clone_trc			(Trc trc);

void * _get_value		(Trc trc);
void _increment_counter	(Trc trc);
void _decrement_counter	(Trc trc);
void _set_value			(Trc trc, void * value);


struct node * add_value_indetermine(int index,void *value, ...);
void *get_value(struct node * h, int index);
void free_node(struct node * h);
#endif	