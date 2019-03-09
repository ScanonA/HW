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
int nThreads;
int volatile cancel_flag = 0;
pthread_mutex_t mutex;

typedef struct {
	int64_t n, i, max;
}data;

data *crit;
/// primality test, if n is prime, return 1, else return 0
int isPrime_initial(int64_t n)
{
     if( n <= 1) return 0; // small numbers are not primes
     if( n <= 3) return 1; // 2 and 3 are prime
     if( n % 2 == 0 || n % 3 == 0) return 0; // multiples of 2 and 3 are not primes
		 int64_t i = 5;
	   int64_t max = sqrt(n);
	   printf("%ld\n", max);
	   while( i <= max) {
	       if (n % i == 0 || n % (i+2) == 0) return 0;
	       i += 6;
	   }
	   return 1;
}

int isPrime_post(int64_t n, int64_t i, int64_t max) {
  if( n <= 1) return 0; // small numbers are not primes
  if( n <= 3) return 1; // 2 and 3 are prime
  if( n % 2 == 0 || n % 3 == 0) return 0; // multiples of 2 and 3 are not primes
  while( i <= max) {
      if (n % i == 0 || n % (i+2) == 0) return 0;
      i += 6;
  }
  return 1;
}

void *findPrime(void * d) {
	data *crit = (data*) d;
	printf("crit: %ld\n", crit->n);
	if(isPrime_post(crit->n, crit->i, crit->max)) {
		if(cancel_flag) pthread_exit(0);
		pthread_mutex_lock(&mutex);
			printf("multithreaded prime: %ld\n", crit->n);
			count++;
			cancel_flag=1;
		pthread_mutex_unlock(&mutex);
	}
	pthread_exit(0);
}


int main( int argc, char ** argv) {
    /// parse command line arguments
    nThreads = 1;
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
    int64_t num_list[MAX_SIZE];
    int nTotal = 0;
    while(1) {
      if(1 != scanf("%ld", & num_list[nTotal])) break;
      nTotal++;
    }



		//calculating prime
    for(int n=0; n<nTotal; n++) {
			int64_t num = num_list[n];
			//printf("%ld\n", num);
      if(sqrt(num)<=5 || nThreads>(sqrt(num))-5) {
				if(num==5 || num==7) {
					printf("not multithreaded prime: %ld\n", num);
					count++;
				}
				else{
					 if(isPrime_initial(num)) {
						 printf("not multithreaded prime: %ld\n", num);
						 count++;
					 }
				}
			}else {
				//printf("else\n");
				int64_t temp, temp_2;
				crit =new data[nThreads];
				temp = round(sqrt(num) - float(5));
				int64_t T = round((double)temp / (double)6);
				int64_t f_Partition = T%nThreads;
				temp = (T/nThreads) + 1;
				temp_2 = T/nThreads;
				int64_t i = 5;
				int64_t max;

				if(f_Partition != 0) {
					max = (temp*6)-1;
				}else {
					max = temp_2*6;
				}
				//printf("else\n");
				for(int64_t l=0; l<f_Partition; l++) {
					//printf("inside first for loop\n");
					crit[l].n = num;
					//printf("%ld\n", crit[l].n);
					crit[l].i = i;
					crit[l].max = max;
					//printf("l in first loop: %ld\n",l);
					if ((max-5)%6!=0){
		        i = max+5;
		      }
		      else{
		        i = max+6;
		      }
		      max =temp*6 + i;
				}

				if (max!= (temp_2*6)-1) {
						max = i + (temp_2*6) +12;
				}

				for(int64_t l=f_Partition; l<nThreads; l++) {
					//printf("inside second for loop----fpartition: %ld-------l: %ld\n", f_Partition, l);
					crit[l].n = num;
					//printf("%ld\n", crit[l].n);
					crit[l].i = i;
					crit[l].max = max;
					if((max-5)%6!=0) {
						i = max + 5;
					}else {
						i = max;
					}
					max = temp_2*6 + i;
				}
				printf("creating threads\n");
				for(int l=0; l<nThreads; l++) {
	           long status = pthread_create(&threads[l], NULL, findPrime, (void *) &crit[l]);
	           if(status != 0) {
	               printf("Error in pthread_create\n");
	               exit(-1);
	             }
	           }
	           for(int l=0; l<nThreads; l++) {
	             pthread_join(threads[l], NULL);
	           }
						 cancel_flag = 0;
          }
      }

    /// report results
    printf("Found %ld primes.\n", count);

    return 0;
}
