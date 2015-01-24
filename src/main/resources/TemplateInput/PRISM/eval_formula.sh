#!/bin/bash
$PARAMS_BASH$

sed -e '1,2d' $REPLACE_BASH$ $1 |  gawk '{print "scale=20;"$0}' | bc
exit 0;
