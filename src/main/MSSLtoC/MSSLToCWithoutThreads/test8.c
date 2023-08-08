#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

Trc  f1(Trc x){
	return 	x;
	free(*(int **)_get_value(x));_destroy(x);

}

int main(int argc, char const *argv[]){	
	int _1=0;
	int * _2 = &_1;
	Trc x= _create_trc (_2);
	int * _3 = (int *) malloc(sizeof(int));
	*_3 = 0;
	Trc a= _create_trc (&_3);
	free(*(int **)_get_value(a));_destroy(a);
