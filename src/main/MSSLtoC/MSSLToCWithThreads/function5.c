#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>
ft_scheduler_t sched;
ft_thread_t _th1;
struct node *__th1;
ft_thread_t _th2;
struct node *__th2;

void foo(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	Trc *y = (Trc *) get_value(args,1);	
	int *z = (int *) get_value(args,2);	
	
	*((int *)_get_value(x))=1;
	 ft_thread_cooperate();
	_destroy(x);
	_destroy(*y);free(y);
	free(z);

}
void bar(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	int y = (int ) get_value(args,1);	
	Trc z = (Trc ) get_value(args,2);	
	
	*((int *)_get_value(x))=1;
	 ft_thread_cooperate();
	Trc _1= _clone_trc (x);
	Trc _2= _clone_trc(z);
	Trc * _3 = malloc(sizeof(Trc));
	*_3 = _2;
	int * _4 = (int *) malloc(sizeof(int));
	*_4 = 1;
	__th2 = add_value_indetermine(2, _1, _3, _4);
	_th2 = ft_thread_create(sched,foo,NULL,__th2);
	_destroy(x);
	
	_destroy(z);

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _5=0;
	int * _6 = &_5;
	Trc x= _create_trc (_6);
	int _7=1;
	int * _8 = &_7;
	Trc y= _create_trc (_8);
	int * a = (int *) malloc(sizeof(int));
	*a = 1;
	Trc _9= _clone_trc (x);
	Trc _10= _clone_trc (y);
	__th1 = add_value_indetermine(2, _9, & *a, _10);
	_th1 = ft_thread_create(sched,bar,NULL,__th1);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	ft_free(_th1);
	pthread_join (ft_pthread(_th2),&retval);
	ft_free(_th2);
	ft_scheduler_free(sched);
	free_node(__th2);
	free_node(__th1);
	free(a);
	_destroy(x);
	_destroy(y);
	return 0;
}