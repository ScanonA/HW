#include <iostream>
#include <fstream>
#include <sstream>
#include <stdlib.h>
#include <algorithm>
#include <list>
#include <limits.h>
#include <string>
#include <vector>

using namespace std;

typedef struct {
	vector<int> holdingNode;
	int requests;
} nodes;
nodes *graph;


int main (int argc, char * const argv[]) {
  ifstream config;
  string conffile;

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

    std::string line;
    bool eof_flag = false;

    while (!eof_flag) {
        bool pound_flag = false;
        while(!pound_flag) {
          std::getline(config, line);
          //cout << line << endl;
          if(line.find("#")) {
            pound_flag = true;
          }
          if(config.eof()) {
            pound_flag = true;
            eof_flag = true;
            break;
          }
          std::istringstream iss(line);
          replace(line.begin(), line.end(), '-',' ');
          int process, resource;
  		  string arrow;
          iss >> process >> arrow >> resource;
          resource += 10000;
          if(arrow.compare("<")) {
          	graph[process].holdingNode.push_back(resource);
          	graph[resource].requests+=1;
          } 
          if(arrow.compare(">")) {
          	graph[resource].holdingNode.push_back(process);
          	graph[process].requests+=1;
          }
          cout << process << arrow << resource << endl;
		}
	}
}
