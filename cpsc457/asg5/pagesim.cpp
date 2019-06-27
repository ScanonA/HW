/*
############################################################
# First Name: Steven
# Last Name: Canon-Almagro
# Student ID: 10155792
# Course: CPSC457
# Tutorial: TUT04
# Assigment: 5
# Question: 6
# File Name: pagesim.cpp
############################################################
*/
//this code simulates the optimal, LRU, and Clock page replacement algorithms

#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <unordered_map>

using namespace std;

vector<int> ref_string;
int frame_num;

//----------------------------------optimal algorithm---------------------------
void optimal() {
  unordered_map<int, int> frame_count;
  int page_faults = 0;
  int frame [frame_num];
  bool hit = false;

  for(int i=0; i<frame_num; i++) {
    frame[i] = -1;
  }
//----here is thte start of the algortihm----
  for(int i=0; i<ref_string.size(); i++) {

//checks to see if there is a hit
    for(int j=0; j<frame_num; j++) {
      if(frame[j] == ref_string[i]) {
        hit = true;
        //cout<<"hit is true** ";
      }
    }

//only checks at the start of the execution
    if(hit == false) {
      for(int j=0; j<frame_num; j++) {
        if(frame[j] == -1) {
          frame[j] = ref_string[i];
          //cout << "start* ";
          hit = true;
          j = frame_num;
          page_faults++;
        }
      }
    }

//when there is no hits and all frames are taken
    if(hit == false) {
      //cout<< "hit is false*** ";
      //calculating all frames and when they will be used again
      for(int j=0; j<frame_num; j++) {
        for(int l=i; l<(ref_string.size()); l++) {
          if(frame[j]!=ref_string[l]) frame_count[j] += 1;
          if(frame[j]==ref_string[l]) l = ref_string.size();
        }
      }
      int farthest_page = -1;
      int index;
      //checking if which frame is not being used for the longest time
      for (auto itr = frame_count.begin(); itr != frame_count.end(); ++itr) {
        if(farthest_page == -1) {
          farthest_page = itr-> second;
          index = itr -> first;
        }
        else {
          if(farthest_page <= itr->second) {
            farthest_page = itr -> second;
            index = itr -> first;
          }
        }
        //cout<<itr->first<<" "<<itr->second<<endl;
      }
      frame_count.clear();
      frame[index] = ref_string[i];
      //cout << "farthest and index: "<< farthest_page << " "<< index<<" ";
      page_faults++;
    }
    //cout<<"reference: "<<ref_string[i];
    //cout<<" frame: ";
    //for(int j=0; j<frame_num; j++) {
    //  cout<<frame[j]<<" ";
    //}
    //cout<<endl;
    hit = false;
  }
  cout<<"\t- frame: ";
  for(int j=0; j<frame_num; j++) {
    cout<<frame[j]<<" ";
  }
  cout<<endl;
  cout<<"\t- page faults: "<<page_faults<<endl;
}

//--------------------------------LRU algorithm--------------------------------
void lru() {
  unordered_map<int, int> frame_count;
  int page_faults = 0;
  int frame [frame_num];
  bool hit = false;

  for(int i=0; i<frame_num; i++) {
    frame[i] = -1;
    frame_count[i] = 0;
  }
//----here is thte start of the algortihm----
  for(int i=0; i<ref_string.size(); i++) {

//checks to see if there is a hit
    for(int j=0; j<frame_num; j++) {
      if(frame[j] == ref_string[i]) {
        hit = true;
        frame_count[j] = 0;
        //cout<<"hit is true** ";
      }
    }

//only checks at the start of the execution
    if(hit == false) {
      for(int j=0; j<frame_num; j++) {
        if(frame[j] == -1) {
          frame[j] = ref_string[i];
          //cout << "start* ";
          hit = true;
          j = frame_num;
          page_faults++;
        }
      }
    }

//when there is no hits and all frames are taken
    if(hit == false) {
      //cout<< "hit is false*** ";
      int least_used = -1;
      int index;
      //checking if which frame is not being used for the longest time
      for (auto itr = frame_count.begin(); itr != frame_count.end(); ++itr) {
        if(least_used == -1) {
          least_used = itr-> second;
          index = itr -> first;
        }
        else {
          if(least_used <= itr->second) {
            least_used = itr -> second;
            index = itr -> first;
          }
        }
        //cout<<itr->first<<": "<<itr->second<<endl;
      }
      //frame_count.clear();
      frame[index] = ref_string[i];
      frame_count[index] = 0;
      //cout << "farthest and index: "<< farthest_page << " "<< index<<" ";
      page_faults++;
    }
    //cout<<"reference: "<<ref_string[i];
    //cout<<" frame: ";
    //for(int j=0; j<frame_num; j++) {
    //  cout<<frame[j]<<" ";
    //}
    //cout<<endl;
    hit = false;
    for (auto itr = frame_count.begin(); itr != frame_count.end(); ++itr) {
      if(frame[itr->first] != -1) {
        itr->second++;
      }
    }
  }
  cout<<"\t- frame: ";
  for(int j=0; j<frame_num; j++) {
    cout<<frame[j]<<" ";
  }
  cout<<endl;
  cout<<"\t- page faults: "<<page_faults<<endl;
}

//---------------------------------clock algortihm-----------------------------
void clock_alg() {
  int page_faults = 0;
  int frame [frame_num];
  int clock_int[frame_num];
  int pointer=0;
  bool hit = false;

  for(int i=0; i<frame_num; i++) {
    frame[i] = -1;
  }

  for(int i=0; i<frame_num; i++) {
    clock_int[i] = 0;
  }

  //--start of the algorithm --
  for(int i=0; i<ref_string.size(); i++) {

    hit =false;
    //checking if there is a hit
    for(int j=0; j<frame_num; j++) {
      //cout<<frame[j]<< " =? " << ref_string[i];
      if(frame[j] == ref_string[i]) {
        //cout<<" hit"<<endl;
        hit = true;
        clock_int[j] = 1;
        break;
      }
      //cout<<" clock-> "<<clock_int[j]<<"  ";
    }
      //cout<<" pointer: "<<pointer;
      //cout<<" ref: "<<ref_string[i];
      //cout<<endl;

    //if the numbers in the frame match the reference string we go here
    if(hit==false) {
      int ptemp = -1;
      while (1) {
        if(clock_int[pointer] == 0) { // then save the index of the clock pointer and move arm 
          ptemp = pointer;
          if(pointer == frame_num-1) pointer = 0;
          else pointer++;
          break;
        } 
        else { //if clock reference is 0 then it changes it to 0 and moves the arm forward 
          clock_int[pointer] = 0;
          if(pointer == frame_num-1) pointer = 0;
          else pointer++;
        }
      }
      //replaces frame where the clock was pointing with the reference string
      frame[ptemp] = ref_string[i];
      clock_int[ptemp] = 1;
      page_faults++;
    }
    //cout<<"reference: "<<ref_string[i];
    //cout<<" frame: ";
    //for(int j=0; j<frame_num; j++) {
    //  cout<<frame[j]<<" ";
    //}
    //cout<<endl;
  }

  cout<<"\t- frame: ";
  for(int j=0; j<frame_num; j++) {
    cout<<frame[j]<<" ";
  }
  cout<<endl;
  cout<<"\t- page faults: "<<page_faults<<endl;
}


//---------------------------------main----------------------------------------

int main (int argc, char * const argv[]) {
	//cout<<argc<<endl;
  if(argc==1) {
    cout<<"Too few arguments"<< "\n"<<"please enter the number of frames as argument"<<"\n"<<"Format: ./pagesim <number of frames> < <textname.txt>"<<endl;
    return 0;
  }
  else if(argc>2) {
    cout<<"Too many argmuments"<<"\n"<<"only enter the number of frames as argument"<<"\n"<<"Format: ./pagesim <number of frames> < <textname.txt>"<<endl;
    return 0;
  }
  string str = "";
  frame_num = atoi(argv[1]);
	while(getline(cin,str)) {
    stringstream ss(str);
    int page;
    while(!ss.eof()) {
      ss>>page;
      ref_string.push_back(page);
      //cout<<page<<endl;
      ss.get();
    }
  }
  cout<<"Optimal:"<<endl;
  optimal();
  cout<<"LRU:"<<endl;
  lru();
  cout<<"Clock:"<<endl;
  clock_alg();

}
