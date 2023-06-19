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
ft_thread_t _th4;

void th2(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	 ft_thread_cooperate();
	 ft_thread_cooperate();

}
void th1(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	Trc _1= _clone_trc (x);
	struct node *__th2;
	__th2 = add_value_indetermine(0, _1);
	_th4 = ft_thread_create(sched,th2,NULL,__th2);

}
void th4(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	 ft_thread_cooperate();
	*((int *)_get_value(x))=2;

}
void th3(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	*((int *)_get_value(x))=1;
	 ft_thread_cooperate();
	Trc _2= _clone_trc (x);
	struct node *__th4;
	__th4 = add_value_indetermine(0, _2);
	_th3 = ft_thread_create(sched,th4,NULL,__th4);

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _3=0;
	int * _4 = &_3;
	Trc x= _create_trc (_4);
	Trc _5= _clone_trc (x);
	struct node *__th1;
	__th1 = add_value_indetermine(0, _5);
	_th2 = ft_thread_create(sched,th1,NULL,__th1);
	Trc _6= _clone_trc (x);
	struct node *__th3;
	__th3 = add_value_indetermine(0, _6);
	_th1 = ft_thread_create(sched,th3,NULL,__th3);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	pthread_join (ft_pthread(_th2),&retval);
	pthread_join (ft_pthread(_th3),&retval);
	pthread_join (ft_pthread(_th4),&retval);
	return 0;
}