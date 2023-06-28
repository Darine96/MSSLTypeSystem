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

void f1(void* args){	
	ft_event_t s1 = (ft_event_t ) get_value(args,0);	
static void* arr[]= {};
    int taille = 0;
    char str[20];
    strcpy(str, __func__);
	 { ft_thread_when_event(s1);
	if(ft_thread_get_case_watch()){
             if(ft_thread_compare_function(str)){
                int index = ft_thread_goto(taille);
               _reset_event_in_list(s1);
                goto *arr[index];
            }
             else{
                ft_thread_return_function(taille);
                _reset_event_in_list(s1);
                return; }
         }{

}	_reset_event_in_list(s1);};
	

}
void f2(void* args){	
	ft_event_t s1 = (ft_event_t ) get_value(args,0);	
static void* arr[]= {};
    int taille = 0;
    char str[20];
    strcpy(str, __func__);
	 ft_thread_generate(s1);
	

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	 ft_event_t s1 = ft_event_create (sched);
	__th2 = add_value_indetermine(0, s1);
	_th2 = ft_thread_create(sched,f1,NULL,__th2);
	__th1 = add_value_indetermine(0, s1);
	_th1 = ft_thread_create(sched,f2,NULL,__th1);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	ft_free(_th1);
	pthread_join (ft_pthread(_th2),&retval);
	ft_free(_th2);
	ft_scheduler_free(sched);
	free_node(__th2);
	free_node(__th1);
	
	return 0;
}