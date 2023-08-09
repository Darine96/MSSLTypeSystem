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

int  f1(	 ft_thread_cooperate();
	int s=1;
	
	return s;

}
void f2(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	ft_event_t s1 = (ft_event_t ) get_value(args,1);	
	Trc* a=& x;
	f1(;
	
	*((int *)_get_value(*a))=1;
	 ft_thread_cooperate();
	_destroy(x);
	
	

}
void f3(void* args){	
	Trc x = (Trc ) get_value(args,0);	
printf("*x = %i",*((int*)_get_value(x)));
	_destroy(x);

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _1=0;
	int * _2 = &_1;
	Trc x= _create_trc (_2);
	 ft_event_t s1 = ft_event_create (sched);
	Trc _3= _clone_trc (x);
	__th2 = add_value_indetermine(1, _3, s1);
	_th2 = ft_thread_create(sched,f2,NULL,__th2);
	Trc _4= _clone_trc (x);
	__th1 = add_value_indetermine(0, _4);
	_th1 = ft_thread_create(sched,f3,NULL,__th1);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	pthread_join (ft_pthread(_th2),&retval);
	free_node(__th2);
	free_node(__th1);
	_destroy(x);
	
	return 0;
}