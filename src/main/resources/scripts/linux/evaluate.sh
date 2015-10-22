START=$(date +%s.%N)
./eval_formula.sh formula.out
END=$(date +%s.%N)
DIFF=$(echo "($END - $START) * 1000" | bc)
printf "\n"
echo 'T:'$DIFF
echo 'Parametric formula with heuristics evaluated in '$DIFF 's'
printf "\n"
