#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>
ft_scheduler_t sched;
ft_thread_t _th1;
struct node *__th1;

void th1(void* args){	
	Trc y = (Trc ) get_value(args,0);	
	
	*((int *)_get_value(y))=1;
{
	Trc x= _clone_trc (y);
	_destroy(x);

};
	_destroy(y);

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _1=0;
	int * _2 = &_1;
	Trc x= _create_trc (_2);
{
	Trc y= _clone_trc (x);
	_destroy(y);

};
	Trc _3= _clone_trc (x);
	__th1 = add_value_indetermine(0, _3);
	_th1 = ft_thread_create(sched,th1,NULL,__th1);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	ft_free(_th1);
	ft_scheduler_free(sched);
	free_node(__th1);
	_destroy(x);
	return 0;
}