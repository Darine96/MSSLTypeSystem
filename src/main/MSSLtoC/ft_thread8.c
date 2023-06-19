#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>
ft_scheduler_t sched;
ft_thread_t _th1;
ft_thread_t _th2;

void th2(void* args){	
	Trc x = (Trc ) get_value(args,0);	
{
	int* a=& *((int *)_get_value(x));
	*a=1;

};
	 ft_thread_cooperate();
{
	int* a=& *((int *)_get_value(x));
	*a=2;

};
	 ft_thread_cooperate();
{
	int* a=& *((int *)_get_value(x));
	*a=3;

};
	 ft_thread_cooperate();
{
	int* a=& *((int *)_get_value(x));
	*a=4;

};

}
void th1(void* args){	
	Trc *y = (Trc *) get_value(args,0);	
	 Trc x=*y;
	 ft_thread_cooperate();
	Trc _1= _clone_trc (x);
	struct node *__th2;
	__th2 = add_value_indetermine(0, _1);
	_th2 = ft_thread_create(sched,th2,NULL,__th2);
	 ft_thread_cooperate();
	 ft_thread_cooperate();

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _2=0;
	int * _3 = &_2;
	Trc x= _create_trc (_3);
	Trc _4= _clone_trc(x);
	Trc * y = malloc(sizeof(Trc));
	*y = _4;
	struct node *__th1;
	__th1 = add_value_indetermine(0, y);
	_th1 = ft_thread_create(sched,th1,NULL,__th1);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	pthread_join (ft_pthread(_th2),&retval);
	return 0;
}