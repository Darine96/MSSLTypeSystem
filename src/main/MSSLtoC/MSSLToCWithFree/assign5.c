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
	Trc ** x = malloc(sizeof(Trc*));
	*x = malloc(sizeof(Trc));
	**x = _2;
	int * _3 = (int *) malloc(sizeof(int));
	*_3 = 0;
	Trc y= _create_trc (&_3);
	Trc a= _clone_trc (y);
	_destroy(a);
	a= _clone_trc (y);
	_destroy(a);
	free(*(int **)_get_value(**x));_destroy(**x);free(*x);free(x);
	free(*(int **)_get_value(y));_destroy(y);
	return 0;
}