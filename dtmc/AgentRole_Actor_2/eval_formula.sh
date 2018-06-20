#!/bin/bash
rTaskG5_T5="0.999";
rTaskG6_T6="0.999";


sed   -e "s/rTaskG5_T5/$rTaskG5_T5/g" -e "s/rTaskG6_T6/$rTaskG6_T6/g" $1 |  gawk '{print "scale=20;"$0}' | bc
exit 0;

