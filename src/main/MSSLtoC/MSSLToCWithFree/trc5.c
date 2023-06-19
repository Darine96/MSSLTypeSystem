#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int * x = (int *) malloc(sizeof(int));
	*x = 0;
	Trc y= _create_trc (&*x);
	free(x);
	_destroy(y);
	return 0;
}