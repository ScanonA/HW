#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <math.h>

#define MAX_SIZE 512
#define MAX_NUMS 1000000

char filename[MAX_SIZE];
int argnum;
int ind;
int intArray[MAX_NUMS];

struct thread_arg {
	int start, finish;
	long threadID;
};

int thread_sum(void * data) {
    thread_arg *stuff = static_cast<thread_arg*>(data);
    int threadSum = 0;
    long int ID = (long int) stuff->threadID;

    for (int i = stuff->start; i < stuff->finish; i++) {
        threadSum = threadSum + intArray[ID];
    }

    printf("\tThread %ld: %d\n", (ID + 1), threadSum);

    return threadSum;
}

int main( int argc, char ** argv) {


    // handle command line arguments
    if( argc != 3) {
        fprintf(stderr, "Required Parameters: <filename> <T>");
        exit(-1);
    }
    else {
        stpcpy(filename, argv[1]);
        argnum = atoi(argv[2]);
    }

    // open file
    FILE * fp = fopen(filename, "r");
    if( fp == NULL) {
        perror("popen failed:");
        exit(-1);
    }

    // read in all intArray in the file into array
    char buff[MAX_SIZE];
    ind = 0;
    while(fgets(buff,MAX_SIZE,fp)) {
        int len = strlen(buff);
        int k = atoi(strndup(buff,len));
        intArray[ind] = k;
        ind++;
    }
    fclose(fp);

    pthread_t threads[argnum];
    long i, status;
    for(i = 0; i < argnum; i++) {	
       thread_arg data;
       data.threadID = i;
       data.start = 0;
       data.finish = 0;
       status = pthread_create(&threads[i], NULL, thread_sum, (void*) data);
       if (status != 0) {
           printf("Error in pthread_create\n");
           exit(-1);
       }
    }

    for (i = 0; i < argnum; i++) {
        pthread_join(threads[i], NULL);
    }

    printf("\tSum = %d\n", sum);
    return 0;

}
