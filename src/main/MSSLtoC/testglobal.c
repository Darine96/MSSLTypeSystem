#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int x=0;
	int* _1=& x;
	Trc y= _create_trc (&_1);
	**((int **)_get_value(y))=1;
	Trc * z = malloc(sizeof(Trc));
	*z = y;
	int a=2;
	int* _2=& a;
	*z= _create_trc (&_2);
	**((int **)_get_value(*z))=5;
	return 0;
}