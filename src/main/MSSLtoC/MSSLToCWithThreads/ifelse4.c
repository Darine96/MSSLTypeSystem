#include"fthread.h"
#include"trc.h"
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>


int main(int argc, char const *argv[]){	
	int x=0;
	int y=1;
	if(&x==&y){
printf("x = %i",x);

}	else{
printf("y = %i",y);

};
	
	
	return 0;
}