/****************************
 Last Name:  Kharfan
 First Name:  Lamess
 Student ID: 10150607
 Course: CPSC 457
 Tutorial Section: T02
 Assignment: 2
 Question: 8
 File name: sum.c
 To compile:  gcc sum.c -l pthread -o sum -lm
 To run: ./sum <filename> <T>
*****************************/

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
int T;
int count;
int intArray[MAX_NUMS];
int sum;

void * thread_sum(void * tid) {
    int start, end, lastPart;
    float div = count / T;
    int part = ceil(div);
    int remaining = part * T;
    int threadSum = 0;
    long int itid = (long int) tid;

    if((remaining - count) == 0)
        lastPart = part;
    else
        lastPart = remaining - count;

    if( itid == (T - 1)) {
        start = (itid * part);
        end = start + part - lastPart;
    }
    else {
        start = itid * part;
        end = start + part;
    }

    for (int i = start; i < end; i++) {
        threadSum = threadSum + ints[i];
    }

    printf("\tThread %ld: %d\n", (itid + 1), threadSum);

    sum += threadSum;

    pthread_exit(0);
}

int main( int argc, char ** argv) {

    // handle command line arguments
    if( argc != 3) {
        fprintf(stderr, "add parameters: <filename> <T>");
        exit(-1);
    }
    else {
        stpcpy(filename, argv[1]);
        T = atoi(argv[2]);
    }

    // open file
    FILE * fp = fopen(filename, "r");
    if( fp == NULL) {
        perror("popen failed:");
        exit(-1);
    }

    // read in all intArray in the file into array
    char buff[MAX_SIZE];
    count = 0;
    while(fgets(buff,MAX_SIZE,fp)) {
        int len = strlen(buff);
        int temp = atoi(strndup(buff,len));
        ints[count] = temp;
        count++;
    }
    fclose(fp);

    pthread_t threads[T];
    long i, status;
    sum = 0;
    for(i = 0; i < T; i++) {
        status = pthread_create(&threads[i], NULL, thread_sum, (void *) i);
        if (status != 0) {
            printf("Error in pthread_create\n");
            exit(-1);
