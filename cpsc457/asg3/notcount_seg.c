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
int count = 0;
pthread_mutex_t mutex;


volatile int cancel_flag = 0;

/// primality test, if n is prime, return 1, else return 0
/*void *isPrime(void * num) {
     int64_t n = *(int64_t*) num;
     //if(cancel_flag==1) pthread_exit(0);
     if( n <= 1) return NULL;//pthread_exit(0); // small numbers are not primes
     //if(cancel_flag==1) pthread_exit(0);
     if( n <= 3) //count++; pthread_exit(0); // 2 and 3 are prime
     //if(cancel_flag==1) pthread_exit(0);
     if( n % 2 == 0 || n % 3 == 0) //pthread_exit(0); // multiples of 2 and 3 are not primes
     //if(cancel_flag==1) pthread_exit(0);
     int64_t i = 5;
     //if(cancel_flag==1) pthread_exit(0);
     int64_t max = sqrt(n);
     //if(cancel_flag==1) pthread_exit(0);
     while( i <= max) {
       //if(cancel_flag==1) pthread_exit(0);
         if (n % i == 0 || n % (i+2) == 0) //pthread_exit(0);
         //if(cancel_flag==1) pthread_exit(0);
         i += 6;
         //if(cancel_flag==1) pthread_exit(0);
     }
     //if(cancel_flag==1) pthread_exit(0);
     count++;
     pthread_exit(0);
}*/
void *isPrime(void * num) {
  int64_t n = *(int64_t*) num;
  if( n <= 1) return NULL; // small numbers are not primes
  if( n <= 3) return (void*)1; // 2 and 3 are prime
  if( n % 2 == 0 || n % 3 == 0) return NULL; // multiples of 2 and 3 are not primes
  int64_t i = 5;
  int64_t max = sqrt(n);
  while( i <= max) {
      if (n % i == 0 || n % (i+2) == 0) return NULL;
      i += 6;
  }
  return (void*)1;
}

int main( int argc, char ** argv) {
    /// parse command line arguments
    int nThreads = 1;
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
    int nTotal = 0;

    while(1) {
      if(1 != scanf("%ld", & num[nTotal])) break;
      nTotal++;
    }

    /*for(int i=0; i<nTotal;i++) {
    	printf("num list: %ld\n", num[i]);
    }*/

    //for(int n=0; n<nTotal; n++) {
      //cancel_flag = 0;
      for(i=0; i<nThreads; i++) {
        int64_t data = num[0];
        status = pthread_create(&threads[i], NULL, isPrime, (void *) i);
        if(status != 0) {
          printf("Error in pthread_create\n");
            exit(-1);
          }
      }
      cancel_flag = 1;
      for(i=0; i<nThreads; i++) {
        pthread_join(threads[i], NULL);
      }
      //cancel_flag = 0;
    //}

    /// report results
    printf("Found %ld primes.\n", count);
    /*
    for(int l=0; l<nTotal; l++) {
      printf("list: %ld\n", num[l]);
    }
    */

    return 0;
}
