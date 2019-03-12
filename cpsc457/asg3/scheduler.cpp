#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <math.h>
#include <pthread.h>
#include <cstring>

#define MAX_SIZE 10000

typedef struct {
  int arrival, burst, status, wait, queue;
  char* symbol;
}process;
process *data;

void ui_header(int total, int total_burst) {
  printf("time\t");
  for(int l=0; l<total;l++) {
    printf("P%d\t",l);
  }
  printf("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
}


void updating_data_sjf(int total, int i, bool cpu_free, int save_num) {
	for(int l=0; l<total; l++) {
      if(data[l].arrival == i) data[l].status = 1;
    }

    int isSmallest = MAX_SIZE;
    if(cpu_free) {
      for(int l=0; l<total; l++) {
        if(data[l].status == 1 && data[l].burst > 0) {
          if(data[l].burst < isSmallest)  {
            isSmallest = data[l].burst;
            save_num = l;
          }
        }
      }
    }

    for(int l=0; l<total; l++) {
      if(data[l].status == 0 || data[l].burst <= 0) data[l].symbol = (char *)" ";
      else if(data[l].status == 1) data[l].symbol = (char *)"+";
      else if(data[l].status == 2) data[l].symbol = (char *)".";
    }
}

void updating_data_rr(int total, int i, bool cpu_free, int save_num, int nextUp, int time_slice, int nextUp_cmp) {
	for(int l=0; l<total; l++) {
      if(data[l].arrival == i) {
       data[l].status = 1;
       data[l].queue = nextUp;
      }
    }

    if(cpu_free) {
      for(int l=0; l<total; l++) {
        if(data[l].status == 1 && data[l].burst > 0) {
          if(data[l].queue > nextUp_cmp)  {
            nextUp_cmp = data[l].queue;
            save_num = l;
          }
        }
      }
    }
    if(cpu_free) {
      data[save_num].status = 2;
      data[save_num].queue = 0;
      cpu_free = false;
    }

    for(int l=0; l<total; l++) {
      if(data[l].status == 0 || data[l].burst <= 0) data[l].symbol = (char *)" ";
      else if(data[l].status == 1) data[l].symbol = (char *)"+";
      else if(data[l].status == 2) data[l].symbol = (char *)".";
    }
}

void sjf(int total, int total_burst) {

  ui_header(total, total_burst);
  bool cpu_free = true;
  int i=0;
  int save_num = -1;

  //printf("%d\n", i);
  //printf("%d\n", total_burst);
  //exit(-1);
  /*
  for(int l=0; l<total; l++) {
    printf("%d ", data[l].arrival);
    printf("%d ", data[l].burst);
    printf("%d\n", data[l].status);
  }
  */

  while(i<=total_burst) {

  	//updating_data(i, cpu_free, save_num);
///*
    for(int l=0; l<total; l++) {
      if(data[l].arrival == i) data[l].status = 1;
    }

    int isSmallest = MAX_SIZE;
    int save_num = -1;
    if(cpu_free) {
      for(int l=0; l<total; l++) {
        if(data[l].status == 1 && data[l].burst > 0) {
          if(data[l].burst < isSmallest)  {
            isSmallest = data[l].burst;
            save_num = l;
          }
        }
      }
    }
    if(cpu_free) {
      data[save_num].status = 2;
      cpu_free = false;
    }

    for(int l=0; l<total; l++) {
      if(data[l].status == 0 || data[l].burst <= 0) data[l].symbol = (char *)" ";
      else if(data[l].status == 1) data[l].symbol = (char *)"+";
      else if(data[l].status == 2) data[l].symbol = (char *)".";
    }

    while(data[save_num].status == 2) {
      printf("%d\t", i);
      for(int l=0; l<total; l++) {
        printf("%s\t", data[l].symbol);
      }
      for(int l=0; l<total; l++) {
      	if(data[l].status == 1) data[l].wait++;
      }
      i++;
      updating_data_sjf(total, i, cpu_free, save_num);
      printf("\n");
      data[save_num].burst--;
      if(data[save_num].burst <= 0) {
        data[save_num].status = 0;
        cpu_free = true;
      }
    }
  }
  float avg_wait=0;
  for(int l=0; l<total; l++) {
  	avg_wait += (float)data[l].wait;
  }
  avg_wait = avg_wait/total; 
  printf("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
  for (int l = 0; l < total; l++) {
  	printf("P%d   waited %.3f sec.\n", l, (float)data[l].wait);
  }
  printf("Average waiting time = %.3f sec.\n", avg_wait);
}

void rr(int time_slice, int total, int total_burst) {
  ui_header(total, total_burst);

  bool cpu_free = true;
  int i=0;
  int save_num = -1;
  int nextUp = 1;
  int nextUp_cmp = 0;

  while(i<=total_burst) {

  	//updating_data(i, cpu_free, save_num);
///*
    for(int l=0; l<total; l++) {
      if(data[l].arrival == i) {
       data[l].status = 1;
       data[l].queue = nextUp;
       data[l].wait = 0;
      }
    }
    nextUp_cmp = 0;
    if(cpu_free) {
      for(int l=0; l<total; l++) {
        if(data[l].status == 1 && data[l].burst > 0) {
          if(data[l].queue > nextUp_cmp)  {
            nextUp_cmp = data[l].queue;
            save_num = l;
          }
        }
      }
    }
    if(cpu_free) {
      data[save_num].status = 2;
      data[save_num].queue = 0;
      cpu_free = false;
    }

    for(int l=0; l<total; l++) {
      if(data[l].status == 0 || data[l].burst <= 0) data[l].symbol = (char *)" ";
      else if(data[l].status == 1) data[l].symbol = (char *)"+";
      else if(data[l].status == 2) data[l].symbol = (char *)".";
    }
    
    int quantum=0;
    while(quantum < time_slice) {
      printf("%d\t", i);
      for(int l=0; l<total; l++) {
        printf("%s\t", data[l].symbol);
      }
      for(int l=0; l<total; l++) {
      	if(data[l].status == 1) data[l].wait++;
      	if(data[l].status == 1) data[l].queue++;
      }
      i++;
      quantum++;
      updating_data_rr(total, i, cpu_free, save_num, nextUp, time_slice, nextUp_cmp);
      printf("\n");
      data[save_num].burst--;
      if(data[save_num].burst <= 0) {
        data[save_num].status = 0;
        cpu_free = true;
        quantum = time_slice;
      }
    }
    cpu_free = true;
    int n=0;
    for (int l=0; l<total; l++) {
    	if (data[l].status==1) {
    		n++;
    	}
    }
    data[save_num].queue = nextUp;
    if(data[save_num].burst>0) data[save_num].status = 1;
  }
  float avg_wait=0;
  for(int l=0; l<total; l++) {
  	avg_wait += (float)data[l].wait;
  }
  avg_wait = avg_wait/total; 
  printf("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
  for (int l = 0; l < total; l++) {
  	printf("P%d   waited %.3f sec.\n", l, (float)data[l].wait);
  }
  printf("Average waiting time = %.3f sec.\n", avg_wait);

}


int main(int argc, char ** argv) {
  char * n_scheduling;
  int t_quantum;
  char * config_file;
  int scheduling_type;
  int total;

  if(argc!=3 & argc!=4) {
    printf("please enter the correct arguments 1. configuration file 2. algorithm scheduling name 3. RR time quantum\n ");
    exit(-1);
  }

  config_file = argv[1];
  n_scheduling = argv[2];

  if(argc==3) {
    if(strcmp(n_scheduling, "sjf")==0 || strcmp(n_scheduling, "SJF")==0) {}else exit(-1);
    scheduling_type = 1;

    printf("success\n");

  }
  if(argc == 4) {
    t_quantum = atoi(argv[3]);
    if(t_quantum<=0) exit(-1);
    if(strcmp(n_scheduling, "rr")==0 || strcmp(n_scheduling, "RR")==0) {}else exit(-1);
    scheduling_type = 2;

    //printf("success\n");

  }


/*
  printf("%s\n", config_file);
  printf("%s\n", n_scheduling);
  printf("%d\n", t_quantum);

  int temp_list[MAX_SIZE];
  int total = 0;
  while(1) {
    if(1 != scanf("%ld", & temp_list[total])) break;
    total++;
  }
*/

  FILE * fp = fopen(config_file, "r");http://mathforum.org/dr.math/faq/faq.prime.num.html
  if( fp == NULL) {
    perror("popen failed:");
    exit(-1);
  }

  //printf("success\n");

  int temp_list[MAX_SIZE];
  int nextChar;
  total = 0;
  while(fscanf(fp, " %d", & nextChar) == 1) {
    temp_list[total] = nextChar;
    total++;
  }
  fclose(fp);

  data = new process[total/2];
  int d = 0;
  int i = 0;
  int total_burst=0;
  while(i<total) {
    data[d].arrival = temp_list[i];
    i++;
    data[d].burst = temp_list[i];
    i++;
    data[d].status = 0;
    data[d].wait = 0;
    total_burst+=data[d].burst;
    d++;
  }

  total= total/2;
  /*
  printf("total: %d\n",total);
  printf("burst: %d\n", total_burst);
  for(int l=0; l<total; l++) {
    printf("%d ", data[l].arrival);
    printf("%d ", data[l].burst);
    printf("%d\n", data[l].status);
  }
*/
  
 if(scheduling_type == 1) sjf(total, total_burst);
 else if(scheduling_type == 2) rr(t_quantum, total, total_burst);

  return 0;
}
