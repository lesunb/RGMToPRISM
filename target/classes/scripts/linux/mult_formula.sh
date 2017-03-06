#!/bin/bash

TERM=$(sed -e '1,3d' $1)
FORMULA='('$TERM')'
for i in `seq 1 $2`;
	do
		FORMULA=$FORMULA'*('$TERM')'
	done
echo $FORMULA > 'formula.out'
exit 0;

