#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int x=0;
	int* y=& x;
	int ** a = malloc(sizeof(int*));
	*a = &*y;
	free(a);
	
	
	return 0;
}