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
	 int* a=*x;
	return 0;
}