#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

Trc  f1(Trc x, int *y){
	return 	x;
	_destroy(x);
	free(y);

}

int main(int argc, char const *argv[]){	
	int * x = (int *) malloc(sizeof(int));
	*x = 0;
	int _1=0;
	int * _2 = &_1;
	Trc _3= _create_trc (_2);
	int ** y = (int **) malloc(sizeof(int*));
	*y = _3;
	int _4=0;
	int * _5 = &_4;
	Trc a= _create_trc (_5);
	_destroy(a);
	int ** _6 = (int **) malloc(sizeof(int*));
	*_6 = &x;
	a = f1(*y, _6);
	_destroy(a);
	free(x);
	free(y);
	return 0;
}