#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int * x = (int *) malloc(sizeof(int));
	*x = 0;
	int ** y = (int **) malloc(sizeof(int*));
	*y = (int *) malloc(sizeof(int));
	**y = 1;
	free(x);
	free(*y);free(y);
	return 0;
}