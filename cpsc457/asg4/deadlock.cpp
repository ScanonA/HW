#include <iostream>
#include <fstream>
#include <sstream>
#include <stdlib.h>
#include <algorithm>
#include <string>

using namespace std;



int main (int argc, char * const argv[]) {
  ifstream config;
  string conffile;
  int process, resource;
  string arrow, line;
  int total_lines;

  if (argc < 2)
  {
      cout << "Usage: banker <config file>\n";
      return 0;
    }
    else
    {
      conffile = argv[1];
    }

    // Open the file
    config.open(conffile.c_str());

    bool eof_flag = false;
    istringstream iss(line);

    while (!eof_flag) {
        bool pound_flag = false;
        while(!pound_flag) {
          getline(config, line);
          replace(line.begin(), line.end(), '-',' ');
          iss.str(line);
          if(line == "#") {
            pound_flag = true;
          }
          if(config.eof()) {
            pound_flag = true;
            eof_flag = true;
            break;
          }
          iss >> process >> arrow >> resource;
          //if(arrow.compare("<"))
          cout << process << arrow << resource << endl;
          iss.clear();
        }
      }
    //cout<<total_lines<<endl;
/*
    istringstream iss(line);
    for(int i=0; i<20; i++) {
      getline(config, line);
      replace(line.begin(), line.end(), '-',' ');
      iss.str(line);
      iss >> process >> arrow >> resource;
      cout << process << arrow << resource << endl;
      iss.clear();
    }
*/
    //cout << process << arrow << resource << endl;
}
