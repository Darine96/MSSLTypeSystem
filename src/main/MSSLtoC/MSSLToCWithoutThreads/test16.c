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
	Trc _5= _clone_trc(x);
	Trc * _6 = malloc(sizeof(Trc));
	*_6 = _5;
;
	Trc _7=  _clone_trc (y);
	Trc * a  = f1(_6, _7);
	_destroy(*a);free(a);
	_destroy(x);
	_destroy(y);
	return 0;
}