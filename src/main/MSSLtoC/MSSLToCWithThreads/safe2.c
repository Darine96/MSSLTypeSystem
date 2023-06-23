#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int _1=1;
	int * _2 = &_1;
	Trc _3= _create_trc (_2);
	int * x = (int *) malloc(sizeof(int));
	*x = _3;
	int * y = (int *) malloc(sizeof(int));
	*y = &*x;
	 ft_thread_cooperate();
	_destroy(*x);free(x);
	free(y);
	return 0;
}