#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>
ft_scheduler_t sched;
ft_thread_t _th1;
ft_thread_t _th2;
ft_thread_t _th3;

void foo(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	Trc *y = (Trc *) get_value(args,1);	
	*((int *)_get_value(x))=1;
	 ft_thread_cooperate();
	Trc _1= _clone_trc (x);
	Trc _2= _clone_trc (*y);
	struct node *__bar;
	__bar = add_value_indetermine(1, _1, _2);
	_th3 = ft_thread_create(sched,bar,NULL,__bar);
	 ft_thread_cooperate();

}
void bar(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	Trc y = (Trc ) get_value(args,1);	
	 ft_thread_cooperate();
{
	int* a=& *((int *)_get_value(x));
	*a=5;

};
	 ft_thread_cooperate();

}
void foofoo(void* args){	
	Trc x = (Trc ) get_value(args,0);	
{
	Trc* y=& x;
	*((int *)_get_value(*y))=2;

};
	 ft_thread_cooperate();

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
	struct node *__foo;
	__foo = add_value_indetermine(1, _7, _9);
	_th2 = ft_thread_create(sched,foo,NULL,__foo);
	Trc _10= _clone_trc (x);
	struct node *__foofoo;
	__foofoo = add_value_indetermine(0, _10);
	_th1 = ft_thread_create(sched,foofoo,NULL,__foofoo);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	pthread_join (ft_pthread(_th2),&retval);
	pthread_join (ft_pthread(_th3),&retval);
	return 0;
}