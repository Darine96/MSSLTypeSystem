#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int _1=0;
	int * _2 = &_1;
	Trc _3= _create_trc (_2);
	Trc ** x = malloc(sizeof(Trc*));
	*x = malloc(sizeof(Trc));
	**x = _3;
	int* y=& *((int *)_get_value(**x));
	 ft_thread_cooperate();
	_destroy(**x);free(*x);free(x);
	
	return 0;
}