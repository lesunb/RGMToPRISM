grammar RTRegex;
rt:     expr NEWLINE                            # printExpr
  |     NEWLINE                                 # blank
  ;

expr:   expr op=('+'|'%'|'@') expr					# gCard
    |	expr op='|' expr						# gAlt
    |	'opt(' expr ')'							# gOpt
    |   'try(' expr ')' '?' expr ':' expr       # gTry
    |	expr op=(';'|'#') expr					# gTime
    |   SKIP									# gSkip
    |   GID                                     # gId
    |   FLOAT									# n
    |   '(' expr ')'                            # parens
    ;

GID     	: [GT] FLOAT		;
FLOAT		: DIGIT+'.'?DIGIT* 	;
SEQ         : ';'			;
INT			: '#'			;
C_SEQ		: '+'			;
C_INT		: '%'			;
C_RTRY		: '@'			;
ALT			: '|'			;
SKIP		: 'skip'		;
NEWLINE 	: [\r\n]+               	;
WS          : [\t]+ -> skip 	;

fragment
DIGIT		: [0-9]			;
