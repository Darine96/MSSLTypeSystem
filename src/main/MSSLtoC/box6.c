#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int x=0;
	int ** y = malloc(sizeof(int*));
	*y = &x;
	**y=5;
	return 0;
}