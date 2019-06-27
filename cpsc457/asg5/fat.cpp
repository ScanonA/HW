// CPSC457 University of Calgary
// Skeleton C++ program for Q7 of Assignment 5.
//
// The program reads in the input, then calls the (wrongly implemented) checkConsistency()
// function, and finally formats the output.
//
// You only need to reimplement the checkConsistency() function.
//
// Author: Pavol Federl (pfederl@ucalgary.ca or federl@gmail.com)
// Date: April 1, 2019
// Version: 5

#include <stdio.h>
#include <string>
#include <set>
#include <vector>
#include <iostream>
#include <unordered_map>

typedef std::string SS;
typedef std::vector<SS> VS;
using namespace std;

struct DEntry {
	SS fname = SS( 4096, 0);
	int size = 0;
	int ind = 0;
	bool tooManyBlocks = true;
	bool tooFewBlocks = false;
	bool hasCycle = true;
	bool sharesBlocks = true;
};

static SS join( const VS & toks, const SS & sep) {
	SS res;
	bool first = true;
	for( auto & t : toks) { res += (first ? "" : sep) + t; first = false;}
	return res;
}

// re-implement this function
//
// Parameters:
//   blockSize - contains block size as read in from input
//   files - array containing the entries as read in from input
//   fat - array representing the FAT as read in from input
// Return value:
//   the function should return the number of free blocks
//   also, for ever entry in the files[] array, you need to set the appropriate flags:
//      i.e. tooManyBlocks, tooFewBlocks, hasCycle and sharesBlocks
int checkConsistency( int blockSize, std::vector<DEntry> & files, std::vector<int> & fat)
{
	set <int> usedBlocks;
	set <int> blocksByFile[files.size()];
	//unordered_map<int, int> blocksToFile;
	int array [files.size()];

	for(int i = 0; i < files.size(); i++) {
		array[i] = 0;
	}


	for (int i = 0; i < files.size(); i++) {
		int index = files[i].ind;

		files[i].hasCycle = false;
		files[i].sharesBlocks = false;
		files[i].tooManyBlocks = false;
		files[i].tooFewBlocks = false;


		while (1){
			if (index == -1) break;

			if (blocksByFile[i].find(index) != blocksByFile[i].end()) {
				files[i].hasCycle = true;
				break;
			}

			else if (usedBlocks.find(index) != usedBlocks.end()) {
				files[i].sharesBlocks = true;
				blocksByFile[i].insert(index);
				//if(blocksToFile[index] != i) {
					//files[i].sharesBlocks = true;
					//blocksToFile[index] = i;
				//}
			}

			else {
				usedBlocks.insert(index);
				//blocksToFile[index] = i;
				blocksByFile[i].insert(index);
				//cout<<"index"<<index<<endl;
			}

			if (index < fat.size()) index = fat[index];

		}

		int blocks = (files[i].size - (files[i].size % blockSize))/blockSize + (((files[i].size % blockSize) != 0) ? 1 : 0);

		//cout << files[i].fname << endl;
		//cout << "\tBlocks: " << blocks << endl;
		//cout << "\tSize: " << files[i].size << endl;

		if (blocksByFile[i].size() > blocks) files[i].tooManyBlocks = true;
		
		else if (blocksByFile[i].size() < blocks) files[i].tooFewBlocks = true;


	}
	/*for(int i=0; i<files.size(); i++) {
		cout<<files[i].fname<<endl;
		for ( std::set<int>::iterator itr = blocksByFile[i].begin(); itr != blocksByFile[i].end(); ++itr ) {
			//cout<<(*itr)<<" ";
		}
		cout<<endl;
	}*/

	for(int i=0; i<files.size(); i++ ) {

		for (std::set<int>::iterator itr = blocksByFile[i].begin(); itr != blocksByFile[i].end(); ++itr) {

			for(int j=i+1; j<files.size(); j++) {
				if(blocksByFile[j].find(*itr) != blocksByFile[j].end()) {
						//cout<<"printing itr: "<<(*itr)<<endl;
						files[i].sharesBlocks = true;
						files[j].sharesBlocks = true;
						break;
					}
			}
					
		}
	}




	/*
	// make the first entry contain no errors
	if( files.size() > 0) {
		files[0].hasCycle = false;
		files[0].tooFewBlocks = false;
		files[0].tooManyBlocks = false;
		files[0].sharesBlocks = false;
	}

	// make the 2nd entry contain one error
	if( files.size() > 1) {
		files[1].hasCycle = true;
		files[1].tooFewBlocks = false;
		files[1].tooManyBlocks = false;
		files[1].sharesBlocks = false;
	}

	// make the 3rd entry contain two errors
	if( files.size() > 2) {
		files[2].hasCycle = false;
		files[2].tooFewBlocks = false;
		files[2].tooManyBlocks = true;
		files[2].sharesBlocks = true;
	}*/

	// finally, return the number of free blocks
	return fat.size() - usedBlocks.size();
}

int main()
{
	try {
		// read in blockSize, nFiles, fatSize
		int blockSize, nFiles, fatSize;
		if( 3 != scanf( "%d %d %d", & blockSize, & nFiles, & fatSize))
			throw "cannot read blockSize, nFiles and fatSize";
		if( blockSize < 1 || blockSize > 1024) throw "bad block size";
		if( nFiles < 0 || nFiles > 50) throw "bad number of files";
		if( fatSize < 1 || fatSize > 200000) throw "bad FAT size";
		// read in the entries
		std::vector<DEntry> entries;
		for( int i = 0 ; i < nFiles ; i ++ ) {
			DEntry e;
			if( 3 != scanf( "%s %d %d", (char *) e.fname.c_str(), & e.ind, & e.size))
				throw "bad file entry";
			e.fname = e.fname.c_str();
			if( e.fname.size() < 1 || e.fname.size() > 16)
				throw "bad filename in file entry";
			if( e.ind < -1 || e.ind >= fatSize) throw "bad first block in fille entry";
			if( e.size < 0 || e.size > 1073741824) throw "bad file size in file entry";
			entries.push_back( e);
		}
		// read in the FAT
		std::vector<int> fat( fatSize);
		for( int i = 0 ; i < fatSize ; i ++ ) {
			if( 1 != scanf( "%d", & fat[i])) throw "could not read FAT entry";
			if( fat[i] < -1 || fat[i] >= fatSize) throw "bad FAT entry";
		}

		// run the consistency check
		int nFreeBlocks = checkConsistency( blockSize, entries, fat);

		// format the output
		size_t maxflen = 0;
		for( auto & e : entries ) maxflen = std::max( maxflen, e.fname.size());
		SS fmt = "  %" + std::to_string( maxflen) + "s: %s\n";

		printf( "Issues with files:\n");
		for( auto & e : entries ) {
			VS issues;
			if( e.tooFewBlocks) issues.push_back( "not enough blocks");
			if( e.tooManyBlocks) issues.push_back( "too many blocks");
			if( e.hasCycle) issues.push_back( "contains cycle");
			if( e.sharesBlocks) issues.push_back( "shares blocks");
			printf( fmt.c_str(), e.fname.c_str(), join( issues, ", ").c_str());
		}
		printf( "Number of free blocks: %d\n", nFreeBlocks);
	}
	catch( const char * err) {
		fprintf( stderr, "Error: %s\n", err);
	}
	catch( ... ) {
		fprintf( stderr, "Errro: unknown.\n");
	}
	return 0;
}
