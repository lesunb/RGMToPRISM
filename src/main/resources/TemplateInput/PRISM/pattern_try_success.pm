[success$PREV_TIME_SLOT$] noError & s$GID$ = 0 -> (s$GID$'=1);//init to running
	[fail$PREV_GID$] noError & s$GID$ = 0 -> (s$GID$'=2);//not used, skip running
