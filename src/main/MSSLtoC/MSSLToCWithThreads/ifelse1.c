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
	Trc a= _clone_trc (x);
{
	int _3=0;
	int * _4 = &_3;
	Trc y= _create_trc (_4);
	_destroy(a);
	a= _clone_trc (y);
	_destroy(y);

};
	Trc b= _clone_trc (x);
	_destroy(a);
	_destroy(b);
	_destroy(x);
	return 0;
}