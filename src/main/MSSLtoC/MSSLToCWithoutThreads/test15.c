#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

Trc  f1(Trc *x, Trc y){
	 Trc _i = *x;
	free(x);
	_destroy(y);
	return _i;

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
	Trc _7= _clone_trc(x);
	Trc * _8 = malloc(sizeof(Trc));
	*_8 = _7;
;
	Trc _9=  _clone_trc (y);
	a = f1(_8, _9);
	_destroy(a);
	_destroy(x);
	_destroy(y);
	return 0;
}