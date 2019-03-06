/**********************************************
 * Last Name:   <Canon-Almagro>
 * First Name:  <Steven>
 * Student ID:  <10155792>
 * Course:      CPSC 457
 * Tutorial:    <T04>
 * Assignment:  1
 * Question:    Q4
 *
 * File name: myWc.cpp
 *********************************************/

#include <unistd.h>
#include <string>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

using namespace std;

int main (int argc, char * const argv[])
{
  // get the file name from command line
  string filename;
  if (argc != 2) {
    cerr << "Usage: readFile <input file> " << endl;
    return -1;
  }
  else {
    filename = argv[1];
  }
  
  // open the file for reading
  int fd = open( filename.c_str(), O_RDONLY);
  if( fd < 0) {
    cerr << "Could not open file " << filename << "\n";
    exit(-1);
  }

  // read file into a buffer then checking buffer to count lines and repeat until end of file
  int count = 0;
  int i;
  while(1) {
    char buffer[1000] = {};
    if(read(fd, & buffer,1000)<1) break;
    for(i=0; i<1000; i++)
		if(buffer[i] == '\n')
			count++;
  }

  // close file and report results
  close( fd);
  cout << count << " " << filename << "\n";
  return 0;
}
