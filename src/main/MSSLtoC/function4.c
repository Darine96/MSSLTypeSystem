#include " fthread.h"
#include "trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>
ft_scheduler_t sched;
ft_thread_t _th1;

void foo(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	*((int *)_get_value(x))=1;
	 ft_thread_cooperate();

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _1=1;
	int * _2 = &_1;
	Trc x= _create_trc (_2);
	Trc _3= _clone_trc (x);
	struct node *__foo;
	__foo = add_value_indetermine(0, _3);
	_th1 = ft_thread_create(sched,foo,NULL,__foo);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	return 0;
}