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
ft_thread_t _th3;
struct node *__th3;

void bar(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	Trc y = (Trc ) get_value(args,1);	
	 ft_thread_cooperate();
{
	int* a=& *((int *)_get_value(x));
	
	*a=5;
	

};
	 ft_thread_cooperate();
printf("*x = %i",*((int*)_get_value(x)));
	_destroy(x);
	_destroy(y);

}
void foo(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	Trc *y = (Trc *) get_value(args,1);	
	
	*((int *)_get_value(x))=1;
	 ft_thread_cooperate();
	Trc _1= _clone_trc (x);
	Trc _2= _clone_trc (*y);
	__th3 = add_value_indetermine(1, _1, _2);
	_th3 = ft_thread_create(sched,bar,NULL,__th3);
	 ft_thread_cooperate();
printf("*x = %i",*((int*)_get_value(x)));
	_destroy(x);
	_destroy(*y);free(y);

}
void foofoo(void* args){	
	Trc x = (Trc ) get_value(args,0);	
{
	Trc* y=& x;
	
	*((int *)_get_value(*y))=2;
	

};
	 ft_thread_cooperate();
	_destroy(x);

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _3=0;
	int * _4 = &_3;
	Trc x= _create_trc (_4);
	int _5=1;
	int * _6 = &_5;
	Trc y= _create_trc (_6);
	Trc _7= _clone_trc (x);
	Trc _8= _clone_trc(y);
	Trc * _9 = malloc(sizeof(Trc));
	*_9 = _8;
	__th2 = add_value_indetermine(1, _7, _9);
	_th2 = ft_thread_create(sched,foo,NULL,__th2);
	Trc _10= _clone_trc (x);
	__th1 = add_value_indetermine(0, _10);
	_th1 = ft_thread_create(sched,foofoo,NULL,__th1);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	ft_free(_th1);
	pthread_join (ft_pthread(_th2),&retval);
	ft_free(_th2);
	pthread_join (ft_pthread(_th3),&retval);
	ft_free(_th3);
	ft_scheduler_free(sched);
	free_node(__th3);
	free_node(__th2);
	free_node(__th1);
	_destroy(x);
	_destroy(y);
	return 0;
}