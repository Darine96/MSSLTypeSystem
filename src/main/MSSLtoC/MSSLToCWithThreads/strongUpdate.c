#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int x=0;
	int * y = (int *) malloc(sizeof(int));
	*y = &x;
	int z=0;
	
	*y=& z;
	
	x=1;
	
	free(y);
	
	return 0;
}