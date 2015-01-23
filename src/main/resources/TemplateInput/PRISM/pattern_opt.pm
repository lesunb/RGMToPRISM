[success$PREV_TIME_SLOT$] noError & s$GID$ = 0 -> (OPT_$GID$) : (s$GID$'=1) + (1 - OPT_$GID$) : (s$GID$'=2);//init to running or skip to final success
	//[success$PREV_TIME_SLOT$] noError & !OPT_$GID$ -> (s$GID$'=2);//not used, skip running
