#!/bin/bash
rTaskG3_T1="0.999";
rTaskG4_T2="0.999";
rTaskG4_T4="0.999";
rTaskG5_T3="0.999";


sed   -e "s/rTaskG3_T1/$rTaskG3_T1/g" -e "s/rTaskG4_T2/$rTaskG4_T2/g" -e "s/rTaskG4_T4/$rTaskG4_T4/g" -e "s/rTaskG5_T3/$rTaskG5_T3/g" $1 |  gawk '{print "scale=20;"$0}' | bc
exit 0;

