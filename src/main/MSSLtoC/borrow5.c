#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int *** _1 = (int ***) malloc(sizeof(int**));
	*_1 = (int **) malloc(sizeof(int*));
	**_1 = (int *) malloc(sizeof(int));
	***_1 = 0;
	Trc x= _create_trc (&_1);
	int** y=& ***((int ****)_get_value(x));
	**y=5;
	int * a = (int *) malloc(sizeof(int));
	*a = 1;
	y=& a;
	**y=5;
	return 0;
}