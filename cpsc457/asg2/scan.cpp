/*
############################################################
# First Name: Steven
# Last Name: Canon-Almagro
# Student ID: 10155792
# Course: CPSC457
# Tutorial: TUT04
# Assigment: 2
# Question: 1
# File Name: scan.sh
############################################################
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <string>

using namespace std;

#define MAX_FNAME_SIZE 512
#define MAX_FILES 1024

//Defining file structure of type and size
typedef struct fl{
	char fName[MAX_FNAME_SIZE];
	int fSize;
}fl;

//this is the sort function that will show the value with the biggest size first
//f1 should be before f2 and should return negative value
int comp_files(const void *f1, const void *f2) {
    return ((fl*)f2)->fSize - ((fl*)f1)->fSize;
}

int scmp(string filename, const char* strCompare) {
	int comparelen = strlen(strCompare);
	int stringlen = filename.length();
	int tempLen = stringlen - comparelen;
	string endComp;

	endComp = filename.substr(tempLen, comparelen);
	if(endComp.compare(strCompare) != 0) {
		return 0;
	}else {
		return 1;
	}

}

int main( int argc, char ** argv) {
  // handle command line arguments
	int numFiles;
	char type[MAX_FNAME_SIZE];

	if( argc != 3) {
		fprintf(stderr, "input two arguments.\n");
		exit(-1);
	}else {
	 //stpcpy(type, "");
	 strcpy(type, argv[1]);
	 sscanf (argv[2], "%i", &numFiles);
 }
  // open 'find...'
    FILE * fp = popen( "./myFind", "r");
    if( fp == NULL) {
    perror("popen failed:");
    exit(-1);
  }
  // read in all filenamesif(scmp(fArray[i].fName, type) == 1) {
  char buff[MAX_FNAME_SIZE];
  int nFiles = 0;
  char * files[MAX_FILES];
  while(fgets(buff,MAX_FNAME_SIZE,fp)) {
    int len = strlen(buff) - 1;
		if(scmp(strndup(buff,len), type) == 1) {
    	files[nFiles] = strndup(buff,len);
			nFiles ++;
		}
  }
  fclose(fp);
	if(numFiles > nFiles)
		numFiles = nFiles;

  printf("Found %d files:\n", numFiles);
  // get file sizes for each file and sum them up
  long long totalSize = 0;
  struct stat st;
	struct fl fArray[MAX_FILES];
  for(int i = 0 ; i < nFiles ; i ++ ) {
    if( 0 != stat(files[i],&st)) {
      perror("stat failed:");
      exit(-1);
    }
		struct fl file;
		strcpy(file.fName, files[i]);
		file.fSize = st.st_size;
		fArray[i] = file;
  }
	qsort(fArray, nFiles, sizeof(fl), comp_files);


  for(int i=0; i<numFiles; i++) {
	  	printf("%s %d\n", fArray[i].fName, fArray[i].fSize);
			totalSize += fArray[i].fSize;
  }

  printf("Total size: %lld bytes.\n", totalSize);
  // clean up
  for(int i = 0; i < nFiles ; i ++ ) {
    free(files[i]);
  }
  // return success
  return 0;
}
