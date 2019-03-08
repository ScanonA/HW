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
pthread_mutex_t mutex;
int64_t count = 0;
/// primality test, if n is prime, return 1, else return 0
void * isPrime(void * num) {
     int64_t n = *(int64_t *)num;
     if( n <= 1) return NULL; // small numbers are not primes
     if( n <= 3) return NULL; // 2 and 3 are prime
     if( n % 2 == 0 || n % 3 == 0) {
     pthread_mutex_lock(&mutex);
     count++;
     pthread_mutex_unlock(&mutex);
     return NULL; // multiples of 2 and 3 are not primes
     }
     int64_t i = 5;
     int64_t max = sqrt(n);
     while( i <= max) {
         if (n % i == 0 || n % (i+2) == 0) return NULL;
         i += 6;
     }
     pthread_mutex_lock(&mutex);
     count++;
     pthread_mutex_unlock(&mutex);
     return NULL;
}

int main( int argc, char ** argv)
{
    /// parse command line arguments
    int nThreads = 1;
    if( argc != 1 && argc != 2) {
        printf("Uasge: countPrimes [nThreads]\n");
        exit(-1);
    }
    if( argc == 2) nThreads = atoi( argv[1]);

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
    int64_t num[MAX_SIZE];
    int nTotal = 0;
    while( 1) {
        if( 1 != scanf("%ld", & num[nTotal])) break;
        nTotal++;
    }
    for(int n=0; n<nTotal; n++) {
      for(int i=0; i<nThreads; i++) {
        long status = pthread_create(&threads[i], NULL, isPrime, (void *) num[n]);
        if(status != 0) {
          printf("Error in pthread_create\n");
            exit(-1);
          }
        }
        for(int i=0; i<nThreads; i++) {
          pthread_join(threads[i], NULL);
        }
    }

    /// report results
    printf("Found %ld primes.\n", count);

    return 0;
}
