#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int x=0;
	int* y=& x;
	int **** z = malloc(sizeof(int***));
	*z = malloc(sizeof(int**));
	**z = &y;
	int *** e = malloc(sizeof(int**));
	*e = malloc(sizeof(int*));
	**e = &*y;
	int f=1;
	int *** _1 = malloc(sizeof(int**));
	*_1 = malloc(sizeof(int*));
	**_1 = &f;
	e=_1;
	return 0;
}