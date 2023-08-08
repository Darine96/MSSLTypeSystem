#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

Trc  f1(Trc x, Trc *y){
	return 	*y;
	_destroy(x);
	_destroy(*y);free(y);

}

int main(int argc, char const *argv[]){	
	int _1=0;
	int * _2 = &_1;
	Trc x= _create_trc (_2);
	int _3=0;
	int * _4 = &_3;
	Trc y= _create_trc (_4);
	Trc  a  = 	f1(y, box(x.clone));
	_destroy(a);
	_destroy(x);
	
	return 0;
}