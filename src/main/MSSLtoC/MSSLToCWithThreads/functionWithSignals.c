#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

void f1(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	ft_event_t s1 = (ft_event_t ) get_value(args,1);	
	 ft_event_t s = ft_event_create (sched);
	 ft_thread_generate(s);
	 ft_thread_when_event(s);
	{

};
	_destroy(x);
	

}

int main(int argc, char const *argv[]){	
	return 0;
}