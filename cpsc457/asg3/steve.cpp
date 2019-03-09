/**********************************************
 * Last Name:   Khanna
 * First Name:  Steve
 * Student ID:  10153930
 * Course:      CPSC 457
 * Tutorial:    04
 * Assignment:  3
 * Question:    Q5
 *
 * File name: count.cpp
 *********************************************/

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <math.h>
#include <vector>
#include <iostream>

using namespace std;


int result=0;

int nThreads;
vector<int64_t> vi;
typedef struct{
    int64_t start, end, value;
} data;
data *params;

int volatile keepRunning;
pthread_t *threads;
int isPrime(int64_t i, int64_t max, int64_t n)
{
  if (n<=1)return 0;
  if (n<=3)return 1;
  if (n%2==0 || n%3==0)return 0;
  while (i<=max){
    if (n%i==0 || n%(i+2)==0)return 0;
    i += 6;
  }
  return 1;
}

void* solve(void *param) {
    data d = *(data*) param;
    if(isPrime(d.start, d.end, d.value)==0) {
      keepRunning = 0;
    }
    return NULL;
}

int check(int64_t value){
  keepRunning =1;
  int counter =0;
  if (sqrt(value)<=5||nThreads > (sqrt(value))-5){
    if (value ==5|| value == 7){
      return 1;
    }
    else{
      if (value<=1)return 0;
      if (value<=3)return 1;
      if (value%2==0 || value%3==0)return 0;
      int64_t i = 5;
      int64_t max =sqrt(value);
      while (i<=max){
        if (value%i==0 || value%(i+2)==0)return 0;
        i += 6;
      }
      return 1;
    }
  }
  else{
    threads = new pthread_t[nThreads];
    params = new data[nThreads];
    //float newNum = (float)(sqrt(value));
    printf("value %ld\n", value);
    float a = (sqrt(value))- float(5);
    printf("a: %ld\n", a);
    int64_t n = nearbyint(a);
    printf("n %ld\n", n);
    double b = (double)n / (double) 6;
    //printf("%f\n", b);
    int64_t nIter= nearbyint(b);
    printf("%ld\n", nIter);
    int64_t firstGroup = nIter%nThreads;
    int64_t secondGroup = nThreads - firstGroup;
    int64_t fNum = (nIter/nThreads) +1;
    int64_t sNum = (nIter/nThreads);
    int64_t startPoint =5;
    int64_t endPoint;

    if (firstGroup!=0){
      endPoint= (fNum*6)-1;
    }
    else {
      endPoint= (sNum*6);
    }

    for(int64_t i=0; i<firstGroup; i++){

      params[i].value= value;
      params[i].start = startPoint;
      params[i].end = endPoint;
      if ((endPoint-5)%6!=0){
        startPoint = endPoint +5;
      }
      else{
        startPoint= endPoint+6;
      }
      endPoint =fNum*6 + startPoint;
    }
    if (endPoint!= (sNum*6)-1){
      endPoint = startPoint + (sNum*6) +12;
    }
    for (int64_t i = firstGroup; i<nThreads; i++){

      params[i].value= value;
      params[i].start = startPoint;
      params[i].end = endPoint;
      if ((endPoint-5)%6!=0){
        startPoint = endPoint +5;
      }
      else{
        startPoint= endPoint;
      }
      endPoint =sNum*6 + startPoint;

    }
    for (int a= 0; a<nThreads; a++){
      pthread_create(&threads[a],NULL,solve, &params[a]);
    }
    for (int i =0; i< nThreads; i++){
      pthread_join(threads[i],NULL);
    }
    if (keepRunning==0){
      return 0;
    }
    return 1;
  }
}

int main(int argc, char ** argv)
{
  nThreads = 1;
  if (argc !=2){
    printf("Usage: countPrimes [nThreads]\n");
    exit(-1);
  }
  if (argc ==2) nThreads = atoi(argv[1]);
  if (nThreads< 1 || nThreads > 256){
    printf("Bad arguments. 1<= nThreads <= 256!\n");
    exit(-1);
  }
  printf("Counting primes using %d threads.\n",nThreads);

  int count, rand;
  int64_t temp;
  while(scanf("%lld", &temp)==1){
    vi.push_back(temp);
  }
  for (long i=0; i<vi.size(); i++){
    result = result + check(vi[i]);
  }
  printf("\nFound %d primes.\n",result);
  delete threads;
  delete params;
  return 0;
}
