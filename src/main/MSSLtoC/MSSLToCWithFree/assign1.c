#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int * y = (int *) malloc(sizeof(int));
	*y = 0;
	free(y);
	int * _1 = (int *) malloc(sizeof(int));
	*_1 = 1;
	y=_1;
	free(y);
	return 0;
}