START=$(date +%s.%N)
#./param-2-2-64 $1 $2 --result-file "${1/.pm/}"
./param "$1.pm" $2 --result-file $1
./mult_formula.sh $1'.out' $3
END=$(date +%s.%N)
DIFF=$(echo "($END - $START) * 1000" | bc)
FILENAME=formula.out
SIZE=$(stat -c%s "$FILENAME")
printf "\n"
echo 'T:'$DIFF
echo 'S:'$SIZE
echo 'Parametric formula generated in '$DIFF' s with '$SIZE' bytes'
printf "\n"
