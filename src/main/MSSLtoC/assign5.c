#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int x=0;
	int* y=& x;
	int * z = (int *) malloc(sizeof(int));
	*z = 1;
	y=&*z;
	return 0;
}