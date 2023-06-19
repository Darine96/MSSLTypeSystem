#include " fthread.h"
#include "trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

void foo(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	Trc y = (Trc ) get_value(args,1);	
	*((int *)_get_value(y))=x;
	 ft_thread_cooperate();

}
void far(void* args){	
	int x = (int ) get_value(args,0);	
	Trc y = (Trc ) get_value(args,1);	
	*((int *)_get_value(y))=1;
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
	Trc _5= _create_trc (_4);
	Trc * y = malloc(sizeof(Trc));
	*y = _5;
	Trc z= _clone_trc (x);
	int a=1;
	ft_scheduler_start (sched);
	return 0;
}