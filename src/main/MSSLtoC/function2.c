#include " fthread.h"
#include "trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

void foo(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	Trc y = (Trc ) get_value(args,1);	
	*y=x;

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _1=0;
	int * _2 = &_1;
	Trc a= _create_trc (_2);
	int _3=1;
	int * _4 = &_3;
	Trc _5= _create_trc (_4);
	Trc * b = malloc(sizeof(Trc));
	*b = _5;
	ft_scheduler_start (sched);
	return 0;
}