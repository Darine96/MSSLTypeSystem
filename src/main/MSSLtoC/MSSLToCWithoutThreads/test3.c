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
	int _1=0;
	int * _2 = &_1;
	Trc x= _create_trc (_2);
	int _3=0;
	int * _4 = &_3;
	Trc y= _create_trc (_4);
	int _5=0;
	int * _6 = &_5;
	Trc a= _create_trc (_6);
	_destroy(a);
	int * _7 = (int *) malloc(sizeof(int));
	*_7 = 0	a = f1(y, _7);
	_destroy(a);
	_destroy(x);
	
	return 0;
}