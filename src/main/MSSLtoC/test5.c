#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int x=0;
	int* y=& x;
	 int a=*y;
	int * z = (int *) malloc(sizeof(int));
	*z = 0;
	return 0;
}