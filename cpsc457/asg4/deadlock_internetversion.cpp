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

class Graph {
	int V; 
	list<int> *adj;
	bool isCyclicUtil(int v, bool visited[], bool *rs);
public:
	Graph(int V);
	void addEdge(int v, int w);
	bool isCyclic();
};

Graph::Graph(int V) {
	this->V = V;
	adj = new list<int>[V];
}

void Graph::addEdge(int v, int w) {
	adj [v].push_back(w);
}

bool Graph::isCyclicUtil(int v, bool visited[], bool *recStack) {

	if(visited[v] == false) {
		visited[v] = true;
		recStack[v] = true;

		list<int>::iterator i;
		for(i = adj[v].begin(); i != adj[v].end(); i++) {
			if (!visited[*i] && isCyclicUtil(*i, visited, recStack))
				return true;
			else if(recStack[*i])
				return true;
		} 
	}
	recStack[v] = false;
	return false;
}

bool Graph::isCyclic() {

	bool *visited = new bool[V];
	bool *recStack = new bool[V];
	for (int i = 0; i < V ; ++i) {
		visited[i] = false;
		recStack[i] = false;
	}

	for (int i = 0; i < V; ++i) {
		if(isCyclicUtil(i, visited, recStack))
			return true;
	}
	return false;
}




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
          //cout << line << endl;
          if(line == "#") {
            pound_flag = true;
          }
          if(config.eof()) {
            pound_flag = true;
            eof_flag = true;
            break;
          }
          iss >> process >> arrow >> resource;

          if(arrow.compare("<")) cout << process << arrow << resource << endl;
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
