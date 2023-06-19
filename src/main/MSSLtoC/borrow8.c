#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int ** x = (int **) malloc(sizeof(int*));
	*x = (int *) malloc(sizeof(int));
	**x = 0;
	int** _1=& *x;
	Trc y= _create_trc (&_1);
	int** a=& **((int ***)_get_value(y));
	**a=5;
	return 0;
}