#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>
ft_scheduler_t sched;
ft_thread_t _th1;
ft_thread_t _th2;

void thread1(void* args){	
	 ft_thread_cooperate();

}
void thread2(void* args){	
	 ft_thread_cooperate();

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	_th2 = ft_thread_create(sched,thread1,NULL,NULL);
	_th1 = ft_thread_create(sched,thread2,NULL,NULL);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	pthread_join (ft_pthread(_th2),&retval);
	return 0;
}