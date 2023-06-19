#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int * x = (int *) malloc(sizeof(int));
	*x = 0;
	*x=1;
	int *** y = malloc(sizeof(int**));
	*y = malloc(sizeof(int*));
	**y = &*x;
	free(x);
	free(*y);free(y);
	return 0;
}