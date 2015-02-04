grammar CtxRegex;
ctx:    'assertion condition 'expr'%'         # printExpr
  |     NEWLINE                                 # blank
  ;

expr:   expr op='<' expr			# cLT
    |	expr op='<=' expr			# cLE
    |	expr op='>' expr			# cGT
    |	expr op='>=' expr			# cGE        
	|	expr op='=' expr			# cEQ
	|	expr op='!=' expr			# cDIFF
	|	expr op='&' expr			# cAnd
	|	expr op='|' expr			# cOr	
	|	BOOL						# cBool  
    |   VAR                         # cVar
    |   FLOAT						# cFloat
    |   '(' expr ')'                # cParens
    ;

BOOL		: [false|true] 				;
VAR     	: ('a'..'z'|'A'..'Z'|'_')+  	;
FLOAT		: DIGIT+'.'?DIGIT* 			;
NEWLINE 	: [\r\n]+             		;
WS	        : (' '|'\t')+ -> skip 			;

fragment
DIGIT		: [0-9]						;
