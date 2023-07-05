#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int x=1;
	int* a=& x;
{
	Trc y= _create_trc (&a);
	Trc z= _clone_trc (y);
	int b=0;
	int* c=& b;
	
	*y=^c;
	
	
	_destroy(y);
	_destroy(z);

};
	
	
	return 0;
}