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

void th2(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	 ft_thread_cooperate();
	*((int *)_get_value(x))=1;

}
void th1(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	Trc y= _clone_trc (x);
	struct node *__th2;
	__th2 = add_value_indetermine(0, y);
	_th3 = ft_thread_create(sched,th2,NULL,__th2);
	*((int *)_get_value(x))=5;
	 ft_thread_cooperate();

}
void th3(void* args){	
	Trc *x = (Trc *) get_value(args,0);	
	Trc y = (Trc ) get_value(args,1);	
	*x=y;
	 ft_thread_cooperate();

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _1=1;
	int * _2 = &_1;
	Trc x= _create_trc (_2);
	Trc _3= _clone_trc(x);
	Trc * y = malloc(sizeof(Trc));
	*y = _3;
	int _4=0;
	int * _5 = &_4;
	Trc z= _create_trc (_5);
	*y= _clone_trc (z);
	Trc _6= _clone_trc (x);
	struct node *__th1;
	__th1 = add_value_indetermine(0, _6);
	_th2 = ft_thread_create(sched,th1,NULL,__th1);
	Trc _7= _clone_trc (x);
	struct node *__th3;
	__th3 = add_value_indetermine(1, y, _7);
	_th1 = ft_thread_create(sched,th3,NULL,__th3);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	pthread_join (ft_pthread(_th2),&retval);
	pthread_join (ft_pthread(_th3),&retval);
	return 0;
}