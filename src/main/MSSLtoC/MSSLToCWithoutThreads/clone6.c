#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

Trc  f1(Trc x, Trc y){
	return 	y;
	_destroy(x);
	_destroy(y);

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
	a = 	f1(y, x);
	_destroy(a);
	
	
	return 0;
}