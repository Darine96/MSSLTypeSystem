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

void f3(void* args){	
	Trc *x = (Trc *) get_value(args,0);	
	ft_event_t s1 = (ft_event_t ) get_value(args,1);	
	ft_event_t s2 = (ft_event_t ) get_value(args,2);	
	static void* arr[2]= {};
    int taille = 0;
    char str[20];
    strcpy(str, __func__);
{
      ft_thread_set_event_watch(s1, str);
      arr[taille]= &&s1;
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
	 { ft_thread_when_event(s2);
	if(ft_thread_get_case_watch()){
             if(ft_thread_compare_function(str)){
                int index = ft_thread_goto(taille);
               ft_thread_reset_event(s2);
                goto *arr[index];
            }
             else{
                ft_thread_return_function(taille);
                ft_thread_reset_event(s2);
                return; }
         }{
	Trc* a=& *x;
	
	**a=1;
	 ft_thread_cooperate();

}	ft_thread_reset_event(s2);};

}//NOM DU SIGNAL
   s1:{ 
      ft_thread_watch_list_not_done();// attention
      taille--;
   }};
	_destroy(x);
	
	
	

}
void f1(void* args){	
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
	
	*x=1;

}	ft_thread_reset_event(s1);};
	 ft_thread_cooperate();
	Trc y= _clone_trc (x);
	Trc * _1 = malloc(sizeof(Trc));
	*_1 = y;
	__th3 = add_value_indetermine(2, _1, s1, s2);
	_th3 = ft_thread_create(sched,f3,NULL,__th3);
	 ft_thread_generate(s1);

}//NOM DU SIGNAL
   s2:{ 
      ft_thread_watch_list_not_done();// attention
      taille--;
   }};

}
void f2(void* args){	
	Trc x = (Trc ) get_value(args,0);	
	ft_event_t s1 = (ft_event_t ) get_value(args,1);	
	ft_event_t s2 = (ft_event_t ) get_value(args,2);	
	int y=1;
	if(&*x==&y)	 ft_thread_generate(s2);

}	else{
	 ft_thread_generate(s1);
	 ft_thread_cooperate();

};

}

int main(int argc, char const *argv[]){	
	void *retval;
	sched = ft_scheduler_create ();
	int _2=0;
	int * _3 = &_2;
	Trc x= _create_trc (_3);
	 ft_event_t s1 = ft_event_create (sched);
	 ft_event_t s2 = ft_event_create (sched);
	Trc _4= _clone_trc (x);
	__th2 = add_value_indetermine(2, _4, s1, s2);
	_th2 = ft_thread_create(sched,f1,NULL,__th2);
	Trc _5= _clone_trc (x);
	__th1 = add_value_indetermine(2, _5, s1, s2);
	_th1 = ft_thread_create(sched,f2,NULL,__th1);
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