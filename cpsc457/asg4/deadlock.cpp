/*
############################################################
# First Name: Steven
# Last Name: Canon-Almagro
# Student ID: 10155792
# Course: CPSC457
# Tutorial: TUT04
# Assigment: 4
# Question: 2
# File Name: deadlock.cpp
############################################################
*/
//this code detects if there is a cycle between nodes and resources given through stdi

#include <iostream>
#include <fstream>
#include <sstream>
#include <stdlib.h>
#include <algorithm>
#include <list>
#include <limits.h>
#include <string>
#include <vector>
#include <unordered_map>

using namespace std;

#define MAX_PROC_NUM 100000
list<int> node;
unordered_map<int, list<int>> holding_nodes;
unordered_map<int, int> request_count;

bool isCycle () {
	//std::cout << "isCycle: "<<endl;
	int not_zeroPrev = -1;
	while(1) {
		int not_zero = 0;
		list<int> deadlock;
		//this for loop iterates through the request_count
		for ( auto itr = request_count.begin(); itr != request_count.end(); ++itr ) {
		//std::cout<< "request_count: " <<itr->first << ":" << itr->second << endl;
		if(itr->second == 0) { //once a 0 is found for a key
			//cout<<itr->first<< " : "<<itr->second << " -> ";
			list<int> temp = holding_nodes[itr->first]; //adding key's list to temp
			std::list<int>::const_iterator iterator;
			for (list<int>:: iterator it = temp.begin(); it != temp.end(); it++) { //this for loop through the key's list
				request_count[*it] -= 1; //uses *it as a key to decrease that keys request_count
				//cout<<*it;
				request_count[itr->first] = -1410065407; //change the key's value to represent it being deleted
			}
			holding_nodes.erase(itr->first);//delete the key's list
			//cout<<endl;
		}
	}
	//cout<<"\n"<<"checking for zero"<<endl;
	//this part is to find how many nodes still have requests
	for ( unsigned i = 0; i < request_count.bucket_count(); ++i) {
		for ( auto it_new = request_count.begin(i); it_new != request_count.end(i); it_new++ ) {
			//std::cout << "checking for zero or not: " <<it_new->first << ":" << it_new->second<<endl;


			if(it_new->second > 0) {
				//cout<< "These are the plus dudes: " <<it_ne
				not_zero++;//is a node still has a request them add
				//cout<<"non-zero: "<<it_new->first<<endl;
				deadlock.push_back(it_new->first);
			}
		}
	}
	//std::cout<<zero<<"/////"<<not_zero << endl;

	if(not_zero == not_zeroPrev) {//if not zero is the same as the previos cycle then we have a deadlock

		deadlock.sort();
		cout<<"Deadlock process: ";
		for (list<int>:: iterator it = deadlock.begin(); it != deadlock.end(); it++) {
			if(*it >= 0) {
				cout<<*it<< " ";
			}
		}
		cout<<endl;
		return false;
	}
	if(not_zero == 0) { //is there are no outgoing requests that means there is no dealock
		cout<<"Deadlock process: none"<<endl;
		return false;
	}
	not_zeroPrev = not_zero; //record previous outgoing request value
}

}

int main (int argc, char * const argv[]) {
	string str;
	while(getline(cin,str)) {
		if(str[0] == '#') {
			isCycle();
			//cout<<"\n\n"<<endl;
			holding_nodes.clear(); //clear unordered_map for next map
			request_count.clear(); //clear unordered_map for next map
		}
			stringstream ss(str);
			int process, resource;
			string arrow;
			ss>>process>>arrow>>resource;
			resource = (resource * -1)-1;
			//cout<<"P:"<<process<<", A:"<<arrow<<", R:"<<resource<<endl;

			if(!arrow.compare("<-")) {
				auto itr = request_count.find(process);
				if(itr != request_count.end()) {}
				else {
					request_count[process] = 0; //initializing node that has no requests
				}

				holding_nodes[process].push_back(resource); //adding holding node
				request_count[resource] += 1; //incrementing when node is requesting
				//cout<<"request_count "<<resource<<": "<<request_count[resource]<<endl;
			}

			if(!arrow.compare("->")) {
				auto itrs = request_count.find(resource);
				if(itrs != request_count.end()) {}
				else {
					request_count[resource] = 0; //initailizing node that has no requests
				}

				holding_nodes[resource].push_back(process);//adding holding node
				request_count[process] += 1;//incrementing when node is requesting
				//cout<<"request_count "<<process<<": "<<request_count[process]<<endl;
			}
		}
		//cout<<"here"<<endl;
		isCycle();
}
