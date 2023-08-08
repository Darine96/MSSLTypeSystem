#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

Trc * f1(Trc *x, Trc y){
	
	_destroy(y);
	return x;

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
	Trc _7= _create_trc (_6);
	Trc * a = malloc(sizeof(Trc));
	*a = _7;
	_destroy(*a);free(a);
	Trc _8= _clone_trc(x);
	Trc * _9 = malloc(sizeof(Trc));
	*_9 = _8;
;
	Trc _10=  _clone_trc (y);
	a = f1(_9, _10);
	_destroy(*a);free(a);
	_destroy(x);
	_destroy(y);
	return 0;
}