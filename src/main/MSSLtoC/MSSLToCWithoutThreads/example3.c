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

void f3(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	ft_event_t s1 = (ft_event_t ) get_value(args,1);	
	ft_event_t s2 = (ft_event_t ) get_value(args,2);	
	static void* arr[2]= {};
    int taille = 0;
    char str[20];
    strcpy(str, __func__);
{
      ft_thread_set_event_watch(s2, str);
      arr[taille]= &&s2;
      taille++;
      
   if(ft_thread_get_case_watch()){
      //case 1: function event same current function:
      if(ft_thread_compare_function(str)){
         //case 2: case_function is true
         if(ft_thread_get_case_function()){
            int index = ft_thread_goto_case_function(taille);// faire attention
            //printf("the index is %d\n", index);
            taille = index + 1;
            goto *arr[index];
         }
         else{
            int index = ft_thread_goto(taille);

            //printf("the index is %d\n", index);
            taille = index + 1;
            goto *arr[index];
         }
      
   }
   else{
      // the watch event generate not in the same current function
      ft_thread_return_function(taille);
      return;
   }
   }{
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
	
	*((int *)_get_value(x))=1;
printf("*x = %i",*((int*)_get_value(x)));

}	ft_thread_reset_event(s1);};
	
	*((int *)_get_value(x))=2;
printf("*x = %i",*((int*)_get_value(x)));

}//NOM DU SIGNAL
   s2:{ 
      ft_thread_watch_list_not_done();// attention
      taille--;
   }};
printf("*x = %i",*((int*)_get_value(x)));
	_destroy(x);
	
	

}
void f2(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	Trc y = (Trc ) get_value(args,1);	
	ft_event_t s1 = (ft_event_t ) get_value(args,2);	
	static void* arr[1]= {};
    int taille = 0;
    char str[20];
    strcpy(str, __func__);
	if(&*((int *)_get_value(x))==&*((int *)_get_value(y)))
{
	 ft_event_t s2 = ft_event_create (sched);
	Trc _1= _clone_trc (x);
	__th2 = add_value_indetermine(2, _1, s1, s2);
	_th2 = ft_thread_create(sched,f3,NULL,__th2);
	 ft_thread_cooperate();
	 ft_thread_generate(s2);
	

}	else{
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
printf("*x = %i",*((int*)_get_value(x)));
printf("*y = %i",*((int*)_get_value(y)));

}	ft_thread_reset_event(s1);};

};
	_destroy(x);
	_destroy(y);
	

}
int  f1(Trc x, Trc *y, ft_event_t s1){	Trc a= _clone_trc (x);
	Trc b= _clone_trc (*y);
	__th1 = add_value_indetermine(2, a, b, s1);
	_th1 = ft_thread_create(sched,f2,NULL,__th1);
;
	_destroy(x);
	
	
	
	

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _2=0;
	int * _3 = &_2;
	Trc x= _create_trc (_3);
	int _4=0;
	int * _5 = &_4;
	Trc y= _create_trc (_5);
	 ft_event_t s1 = ft_event_create (sched);
	f1(x, & y);
	ft_scheduler_start (sched);
	pthread_join (ft_pthread(_th1),&retval);
	pthread_join (ft_pthread(_th2),&retval);
	free_node(__th2);
	free_node(__th1);
	
	_destroy(y);
	
	return 0;
}