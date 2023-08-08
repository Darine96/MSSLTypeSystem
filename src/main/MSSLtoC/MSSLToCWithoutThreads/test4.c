#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

Trc  f1(Trc x, int **y){
	return 	x;
	_destroy(x);
	free(*y);free(y);

}

int main(int argc, char const *argv[]){	
	int * x = (int *) malloc(sizeof(int));
	*x = 0;
;
	int _1=0;
	int * _2 = &_1;
	Trc y= _create_trc (_2);
	int _3=0;
	int * _4 = &_3;
	Trc a= _create_trc (_4);
	_destroy(a);
	int * _5 = (int *) malloc(sizeof(int));
	*_5 = x	a = f1(y, _5);
	_destroy(a);
	
	
	return 0;
}