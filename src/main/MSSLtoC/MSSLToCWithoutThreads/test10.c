#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

int  f1(Trc *x, int y){
	return 	y;
	
	

}

int main(int argc, char const *argv[]){	
	int _1=0;
	int * _2 = &_1;
	Trc x= _create_trc (_2);
	int * _3 = (int *) malloc(sizeof(int));
	*_3 = 0;
	Trc b= _create_trc (&_3);
	int  a  = & b, 1);
printf("a = %i",a);
	
	free(*(int **)_get_value(b));_destroy(b);
	_destroy(x);
	return 0;
}