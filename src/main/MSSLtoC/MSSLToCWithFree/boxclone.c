#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int _1=0;
	int * _2 = &_1;
	Trc _3= _create_trc (_2);
	Trc * x = malloc(sizeof(Trc));
	*x = _3;
	Trc y= _clone_trc (*x);
	_destroy(*x);free(x);
	_destroy(y);
	return 0;
}