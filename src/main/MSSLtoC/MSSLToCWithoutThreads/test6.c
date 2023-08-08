#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

Trc  f1(Trc *x){
	return 	*x;
	_destroy(*x);free(x);

}

int main(int argc, char const *argv[]){	
	int _1=0;
	int * _2 = &_1;
	Trc x= _create_trc (_2);
	int _3=0;
	int * _4 = &_3;
	Trc a= _create_trc (_4);
	_destroy(a);
	Trc _5= _clone_trc(x);
	Trc * _6 = malloc(sizeof(Trc));
	*_6 = _5;
	a = f1(_6);
	_destroy(a);
	_destroy(x);
	return 0;
}