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
	_destroy(x);
	int _3=1;
	int * _4 = &_3;
	x= _create_trc (_4);
	_destroy(x);
	return 0;
}