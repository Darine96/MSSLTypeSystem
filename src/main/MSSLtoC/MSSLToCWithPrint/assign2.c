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
	int** y=& *x;
	
	**y=1;
printf("**x = %i",**x);
	free(*x);free(x);
	
	return 0;
}