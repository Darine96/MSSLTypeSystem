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
	Trc p = (Trc ) get_value(args,0);	
	
	*((int *)_get_value(p))=5;
	 ft_thread_cooperate();
	_destroy(p);

}
void bar(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	Trc _1= _clone_trc (x);
	__th2 = add_value_indetermine(0, _1);
	_th2 = ft_thread_create(sched,foo,NULL,__th2);
	_destroy(x);

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _2=0;
	int * _3 = &_2;
	Trc x= _create_trc (_3);
	Trc _4= _clone_trc (x);
	__th1 = add_value_indetermine(0, _4);
	_th1 = ft_thread_create(sched,bar,NULL,__th1);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	ft_free(_th1);
	pthread_join (ft_pthread(_th2),&retval);
	ft_free(_th2);
	ft_scheduler_free(sched);
	free_node(__th2);
	free_node(__th1);
	_destroy(x);
	return 0;
}