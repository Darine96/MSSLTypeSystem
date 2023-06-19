#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int _1=0;
	int * _2 = &_1;
	Trc x= _create_trc (_2);
	int _3=1;
	int * _4 = &_3;
	Trc y= _create_trc (_4);
	Trc a= _clone_trc (x);
	_destroy(a);
	a= _clone_trc (y);
	_destroy(a);
	_destroy(x);
	_destroy(y);
	return 0;
}