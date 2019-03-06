#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <math.h>
#include <fstream>
#include <string>

#define MAX_NUM 1000000
#define MAX_FNAME_SIZE 512

char filename [MAX_FNAME_SIZE];
int ind;
int argnum;
int intArray[MAX_NUM];
int sum;
//pthread_mutex_t s_mutex;


void * s_thread(void * i) {
  int start, finish, end;
  int cell = (ceil(ind / argnum));
  int rem = argnum * cell;
  int totalSum = 0;
  long int num = (long int)i;

  if((rem - ind) == 0) {
    end = cell;
  }else {
    end = rem - ind;
  }
  if(num == (argnum -1)) {
    start = (num * cell);
    finish = start + cell - end;
  }else {
    start = end * cell;
    finish = start + cell;
  }

  //pthread_mutex_lock(&s_mutex);
  for(int i = start; i <finish; i++) {
    totalSum = totalSum + intArray[i];
  }
//  pthread_mutex_unlock(&s_mutex);

  printf("\tThread %ld: %d\n", (num + 1), totalSum);

//  pthread_mutex_lock(&s_mutex);
  sum += totalSum;
  //pthread_mutex_unlock(&s_mutex);

  pthread_exit(0);
}




int main( int argc, char ** argv) {

  //pthread_mutex_init( &s_mutex, NULL);
  string filename;

  if( argc != 3) {
		fprintf(stderr, "two arguments required.\n");
		exit(-1);
	}else {
	 filename = argv[1];
   printf("%s\n", argv[1]);
   if(argnum <= MAX_NUM) {
	  argnum = atoi(argv[2]);
  } else {
    argnum = MAX_NUM;
  }
 }
/*
 FILE * fp = popen(filename, "r");
 if( fp == NULL) {
   perror("popen failed:");
   exit(-1);
 }

 char buff[MAX_FNAME_SIZE];
 ind = 0;
 while(fgets(buff, MAX_FNAME_SIZE, fp)) {
   int len = strlen(buff);
   int temp = atoi(strndup(buff,len));
   intArray[ind] = temp;
   printf("%d\n", temp);
   ind++;
 }*/

 ifstream file;
 file.open(filename);
 if(file.isopen()) {
   while(true) {
     int num;
     file >> num;
     if(file.eof()) break;
     intArray[ind++] = num;
   }
 }
 file.close();
 
 pthread_t threads[argnum];
 for(long i = 0; i < argnum; i++) {
   long status = pthread_create(&threads[i], NULL, s_thread, (void *) i);
   if(status != 0) {
     printf("Error in pthread_create\n");
     exit(-1);
   }
 }

 for(int i = 0; i < argnum; i++) {
   pthread_join(threads[i], NULL);
 }

 //pthread_mutex_destroy(&s_mutex);
 printf("\tSum = %d\n", sum);
 return 0;

}
