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
	int** a=& x;
	Trc y= _clone_trc (*a);
	
	_destroy(x);
	_destroy(y);
	return 0;
}