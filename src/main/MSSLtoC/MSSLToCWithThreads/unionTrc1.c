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
	Trc y= _clone_trc (x);
	int _3=0;
	int * _4 = &_3;
	Trc z= _create_trc (_4);
	Trc* a=& y;
	
	*a= _clone_trc (z);
	
	_destroy(x);
	_destroy(y);
	_destroy(z);
	return 0;
}