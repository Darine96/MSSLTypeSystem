#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>

void f1(void* args){	
	Trc *x = (Trc *) get_value(args,0);	
	int y = (int ) get_value(args,1);	
printf("***x = %i",***x);
	
	

}

int main(int argc, char const *argv[]){	
	int _1=0;
	int * _2 = &_1;
	Trc x= _create_trc (_2);
	int * _3 = (int *) malloc(sizeof(int));
	*_3 = 0;
	Trc b= _create_trc (&_3);
	f1(& b, 1);
	free(*(int **)_get_value(b));_destroy(b);
	_destroy(x);
	return 0;
}