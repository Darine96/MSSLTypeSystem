#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int a=0;
	int **** x = malloc(sizeof(int***));
	*x = malloc(sizeof(int**));
	**x = malloc(sizeof(int*));
	***x = &a;
	return 0;
}