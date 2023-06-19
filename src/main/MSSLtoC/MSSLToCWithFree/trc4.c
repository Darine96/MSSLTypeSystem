#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int x=0;
	int* _1=&x;
	Trc y= _create_trc (&_1);
{
	int _2=0;
	int * _3 = &_2;
	Trc _4= _create_trc (_3);
	Trc * a = malloc(sizeof(Trc));
	*a = _4;
	_destroy(*a);free(a);

};
	
	_destroy(y);
	return 0;
}