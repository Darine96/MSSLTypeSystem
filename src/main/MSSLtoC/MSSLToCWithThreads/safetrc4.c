#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int * _1 = (int *) malloc(sizeof(int));
	*_1 = 0;
	Trc _2= _create_trc (&_1);
	Trc * x = malloc(sizeof(Trc));
	*x = _2;
	int* y=& **((int **)_get_value(*x));
	 ft_thread_cooperate();
	free(*(int **)_get_value(*x));_destroy(*x);free(x);
	
	return 0;
}