/// counts number of primes from standard input
///
/// compile with:
///   $ gcc findPrimes.c -O2 -o count -lm
///
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <math.h>
#include <pthread.h>

#define MAX_SIZE 10000

int64_t count = 0;
int64_t num[MAX_SIZE];
int nThreads = 1;
int nTotal;
pthread_mutex_t mutex;
pthread_mutex_t mutex2;
pthread_mutex_t mutex3;
int l = 0;

/// primality test, if n is prime, return 1, else return 0
int isPrime(int64_t n)
{
     if( n <= 1) return 0; // small numbers are not primes
     if( n <= 3) return 1; // 2 and 3 are prime
     if( n % 2 == 0 || n % 3 == 0) return 0; // multiples of 2 and 3 are not primes
     int64_t i = 5;
     int64_t max = sqrt(n);
     while( i <= max) {
         if (n % i == 0 || n % (i+2) == 0) return 0;
         i += 6;
     }
     return 1;
}

void * thread_sum(void * tid) {
 
  int div = floor(nTotal/nThreads);

  long int itid= (long int) tid;
  printf("itid: %ld\n", itid);

  int firstgroup_partition=(nTotal%nThreads);
  int secondgroup_partition=(nThreads-nTotal%nThreads);

pthread_mutex_lock(&mutex);
if(itid<firstgroup_partition){
  //pthread_mutex_lock(&mutex);
  for(int i=0;i<div+1;i++) {
  	//pthread_mutex_unlock(&mutex);
    printf("%ld\n", num[l]);
    if(isPrime(num[l])==1) {
          printf("num firstgroup_partition: %ld\n", num[l]);
          //pthread_mutex_lock(&mutex3);
          count++;
          //pthread_mutex_unlock(&mutex3);
     }
     //pthread_mutex_lock(&mutex3);
     l++;
     //pthread_mutex_unlock(&mutex3);
   }
   //pthread_mutex_unlock(&mutex);
}


 else {
 	//pthread_mutex_lock(&mutex2);
   for(int i=0;i<div;i++) {
   	//pthread_mutex_unlock(&mutex2);
   	//printf("%ld\n", num[l]);
     if(isPrime(num[l])==1) {
          printf("num secondgroup_partition: %ld\n", num[l]);
          //pthread_mutex_lock(&mutex3);
          count++;
          //pthread_mutex_unlock(&mutex3);
     }
    //pthread_mutex_lock(&mutex3);
     l++;
     //pthread_mutex_unlock(&mutex3);
   }
   //pthread_mutex_unlock(&mutex2);
}
pthread_mutex_unlock(&mutex);
pthread_exit(0);
}

int main( int argc, char ** argv)
{
    /// parse command line arguments
    if( argc != 1 && argc != 2) {
        printf("Uasge: countPrimes [nThreads]\n");
        exit(-1);
    }
    if( argc == 2) {
      nThreads = atoi( argv[1]); //puts the input into nThreads
    }

    /// handle invalid arguments
    if( nThreads < 1 || nThreads > 256) {
        printf("Bad arguments. 1 <= nThreads <= 256!\n");
    }
    if( nThreads != 1) {
        //printf("I am not multithreaded yet :-(\n");
        //exit(-1);
    }

    /// count the primes
    printf("Counting primes using %d thread%s.\n",
           nThreads, nThreads == 1 ? "s" : "");
    pthread_t threads[nThreads];
    long i, status;
    nTotal = 0;
    while(1) {
      if(1 != scanf("%ld", & num[nTotal])) break;
      nTotal++;
    }

    /*for(int i=0; i<nTotal;i++) {
    	printf("num list: %ld\n", num[i]);
    }*/

    for(i=0; i<nThreads; i++) {
      status = pthread_create(&threads[i], NULL, thread_sum, (void *) i);
      if(status != 0) {
        printf("Error in pthread_create\n");
          exit(-1);
        }
    }
    for(i=0; i<nThreads; i++) {
      pthread_join(threads[i], NULL);
    }

    /// report results
    printf("Found %ld primes.\n", count);
    /*
    for(int l=0; l<nTotal; l++) {
      printf("list: %ld\n", num[l]);
    }
    */

    return 0;
}
