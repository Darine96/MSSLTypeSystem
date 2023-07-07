#include "trc.h"
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>  
#include <stdarg.h>   
/********** struct of Trc ****/
struct RefCount{
	int count;
	void * value;
};

struct Trc
{
	
	RefCount data;//refrence to the counter
};

/****LinkedList ****/
struct node {
   void *value;
   struct node *next;
};

/*** Functions **********/
int _get_counter(Trc trc){
	return trc->data->count;
}

void _decrement_counter(Trc trc){
	RefCount c = trc->data;
	c->count = c->count - 1;
	//trc->counter->count=trc->counter->count--; 
}

void _increment_counter(Trc trc){
	RefCount c = trc->data;
	c->count = c->count + 1;
}

void * _get_value(Trc trc){
	return trc->data->value;
}

void _set_value(Trc trc, void* value){
	trc->data->value = value;
}


Trc _create_trc(void * args){

	//create a counter and intialize int
	Trc trc = (struct Trc *) malloc (sizeof (struct Trc));
	RefCount ref =(struct RefCount *) malloc (sizeof (struct RefCount));
	//create a trc
	ref->value = args;
	ref->count=1;
	trc->data = ref;
	return trc;
}


Trc _clone_trc(Trc trc){

	//increment the counter
	_increment_counter(trc);
	//create a counter
	Trc clone = (struct Trc *) malloc (sizeof (struct Trc));
	//create a clone
	clone->data = trc->data;
	return clone;
}

void _destroy(Trc trc){
	//printf(" counter %i\n", _get_counter(trc));
	if(_get_counter(trc)==1){
		//free
		//printf("hello trc-- destroy --\n");
		free(trc->data);
		free(trc);
	}else{
		//printf("hello clone-- destroy --\n");
		_decrement_counter(trc);
		//RefCount ref = trc->counter;
		trc->data=NULL;
		free(trc);
	}

}

/************* Functions specific to linkedlist ********************/
struct node * add_value_indetermine(int index,void *value, ...) {
		//initialise the liste
		va_list ap;
    	va_start(ap, value);

    	struct node * current = (struct node*) malloc(sizeof(struct node));
    	current->value =value;
    	current -> next = NULL;

    	for (int i = 0; i < index; ++i)
    	{
    		/* code */
    		Trc temp = (va_arg(ap, Trc));
    		struct node * current1 = (struct node*) malloc(sizeof(struct node));
    		current1->value =temp;
    		current1 -> next = NULL;
 
    		// add at the last of the struct
    		struct node *c = current;
   			while(c->next !=NULL) {
     			 c = c->next;
   			}
   			c->next = current1;
   	
    	}

    	
    	return current;
}

void *get_value(struct node * h, int index) {
   struct node *current = h;
   for (int i = 0; i < index; i++) {
   	    current = current->next;
   	   }
   return current->value;
}

void free_node(struct node * h) {
   struct node *current = h;
   while(current!=NULL){
   		struct node *temp = current;
   	    current = current->next;
   	    free(temp);
   	   }
  
}


