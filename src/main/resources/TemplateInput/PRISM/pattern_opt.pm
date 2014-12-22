[success$PREV_TIME_SLOT$] noError & OPT_$GID$ & s$GID$ = 0 -> (s$GID$'=1);//init to running
	[success$PREV_TIME_SLOT$] noError & !OPT_$GID$ -> (s$GID$'=2);//not used, skip running
