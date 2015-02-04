[success$PREV_TIME_SLOT$] $PREV_SUCCESS$ & $CTX_CONDITION$ XOR_$XOR_GIDS$=$XOR_VALUE$ & s$GID$ = 0 -> (s$GID$'=1);//init to running
	[success$PREV_TIME_SLOT$] $PREV_SUCCESS$ & $CTX_CONDITION$ XOR_$XOR_GIDS$!=$XOR_VALUE$ -> (s$GID$'=3);//not used, skip running
	$CTX_EFFECT$
