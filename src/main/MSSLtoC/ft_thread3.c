#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>
ft_scheduler_t sched;
ft_thread_t _th1;

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

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _1=0;
	int * _2 = &_1;
	Trc x= _create_trc (_2);
	int _3=1;
	int * _4 = &_3;
	Trc y= _create_trc (_4);
	Trc _5= _clone_trc (x);
	Trc _6= _clone_trc (y);
	struct node *__bar;
	__bar = add_value_indetermine(1, _5, _6);
	_th1 = ft_thread_create(sched,bar,NULL,__bar);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	return 0;
}