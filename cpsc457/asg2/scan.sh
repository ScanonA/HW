#!/bin/bash
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



suffix=$1
posint=$2

#echo "$1,$2"

find . -type f -name "*$1" -printf '%p %s\n' | sort -k 2 -nr | head -$2 | awk '{ print $1 " " $2 ; total += $2 } END { print "Total size: " total }' 
