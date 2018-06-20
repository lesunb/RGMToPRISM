#!/bin/bash
rTaskG1_T1="0.999";
rTaskG2_T2="0.999";
rTaskG3_T3="0.999";


sed   -e "s/rTaskG1_T1/$rTaskG1_T1/g" -e "s/rTaskG2_T2/$rTaskG2_T2/g" -e "s/rTaskG3_T3/$rTaskG3_T3/g" $1 |  gawk '{print "scale=20;"$0}' | bc
exit 0;

