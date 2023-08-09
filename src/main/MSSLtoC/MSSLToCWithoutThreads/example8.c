#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>
ft_scheduler_t sched;
ft_thread_t _th1;
struct node *__th1;

void f1(void* args){	
	Trc *x = (Trc *) get_value(args,0);	
	Trc y= _clone_trc (*x);
	 ft_thread_cooperate();
	_destroy(*x);free(x);
	_destroy(y);

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _1=0;
	int * _2 = &_1;
	Trc _3= _create_trc (_2);
	Trc * x = malloc(sizeof(Trc));
	*x = _3;
	int _4=0;
	int * _5 = &_4;
	Trc y= _create_trc (_5);
	_destroy(*x);
	*x= _clone_trc (y);
	__th1 = add_value_indetermine(0, x);
	_th1 = ft_thread_create(sched,f1,NULL,__th1);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	free_node(__th1);
	
	_destroy(y);
	return 0;
}