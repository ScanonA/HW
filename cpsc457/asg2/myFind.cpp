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
#include <sys/types.h>
#include <dirent.h>
#include <string.h>

void vDIR(const char *path);

void vDIR(const char *path) {
	DIR *directory = opendir(path);
	if (!directory) return;

	char childPath[1000];

	struct dirent * entry;
	while ((entry = readdir(directory)) != NULL) {
		sprintf(childPath, "%s/%s", path, entry->d_name);
		if(strcmp(".", entry->d_name) && strcmp("..",entry->d_name)) {
			if(entry->d_type != DT_DIR) {
				printf("%s/%s\n", path, entry->d_name);
			}
			vDIR(childPath);
		}
	}
	closedir(directory);
}

int main() {
	vDIR(".");
	return 0;
}
