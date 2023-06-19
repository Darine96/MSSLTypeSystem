#include " fthread.h"
#include "trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

void foo(void* args){	
	int x = (int ) get_value(args,0);	
	int y=1;

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int y=1;
	ft_scheduler_start (sched);
	return 0;
}