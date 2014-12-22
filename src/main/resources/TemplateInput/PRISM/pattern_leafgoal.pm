
const double rGoal$GID$ = 0.999;
$DEC_HEADER$

module $MODULE_NAME$
	s$GID$ :[0..3] init 0;
	
	$DEC_TYPE$
	[] s$GID$ = 1 -> rGoal$GID$ : (s$GID$'=2) + (1 - rGoal$GID$) : (s$GID$'=3);//running to final state
	[success$TIME_SLOT$] s$GID$ = 2 -> (s$GID$'=2);//final state success
	[fail$GID$] s$GID$ = 3 -> (s$GID$'=3);//final state fail
endmodule
