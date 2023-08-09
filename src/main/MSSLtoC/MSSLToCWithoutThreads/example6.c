#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>
ft_scheduler_t sched;
ft_thread_t _th1;
struct node *__th1;
ft_thread_t _th2;
struct node *__th2;
ft_thread_t _th3;
struct node *__th3;

void f1(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	int* a=& *((int *)_get_value(x));
	
	*a=1;
printf("*x = %i",*((int*)_get_value(x)));
	
	*a=1;
printf("*x = %i",*((int*)_get_value(x)));
	_destroy(x);
	

}
void f2(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	ft_event_t s1 = (ft_event_t ) get_value(args,1);	
	static void* arr[1]= {};
    int taille = 0;
    char str[20];
    strcpy(str, __func__);
	int a=2;
	 { ft_thread_when_event(s1);
	if(ft_thread_get_case_watch()){
             if(ft_thread_compare_function(str)){
                int index = ft_thread_goto(taille);
               ft_thread_reset_event(s1);
                goto *arr[index];
            }
             else{
                ft_thread_return_function(taille);
                ft_thread_reset_event(s1);
                return; }
         }{
	if(*((int *)_get_value(x))==a)
{
printf("*x = %i",*((int*)_get_value(x)));

}	else{

};

}	ft_thread_reset_event(s1);};
	_destroy(x);
	
	

}
void f3(void* args){	
	ft_event_t s1 = (ft_event_t ) get_value(args,0);	
	 ft_thread_cooperate();
	 ft_thread_generate(s1);
	

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _1=0;
	int * _2 = &_1;
	Trc x= _create_trc (_2);
	 ft_event_t s1 = ft_event_create (sched);
	Trc _3= _clone_trc (x);
	__th3 = add_value_indetermine(0, _3);
	_th3 = ft_thread_create(sched,f1,NULL,__th3);
	Trc _4= _clone_trc (x);
	__th2 = add_value_indetermine(1, _4, s1);
	_th2 = ft_thread_create(sched,f2,NULL,__th2);
	__th1 = add_value_indetermine(0, s1);
	_th1 = ft_thread_create(sched,f3,NULL,__th1);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	pthread_join (ft_pthread(_th2),&retval);
	pthread_join (ft_pthread(_th3),&retval);
	free_node(__th3);
	free_node(__th2);
	free_node(__th1);
	_destroy(x);
	
	return 0;
}