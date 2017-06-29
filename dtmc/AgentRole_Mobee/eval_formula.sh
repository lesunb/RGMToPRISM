#!/bin/bash
rTaskG8_T1="0.999";
rTaskG9_T1_1="0.999";
rTaskG9_T1_2="0.999";
rTaskG4_T1="0.999";


sed   -e "s/rTaskG8_T1/$rTaskG8_T1/g" -e "s/rTaskG9_T1_1/$rTaskG9_T1_1/g" -e "s/rTaskG9_T1_2/$rTaskG9_T1_2/g" -e "s/rTaskG4_T1/$rTaskG4_T1/g" $1 |  gawk '{print "scale=20;"$0}' | bc
exit 0;

