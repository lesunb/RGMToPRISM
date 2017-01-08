grammar CtxRegex;

@rulecatch {
   catch (RecognitionException e) {
    throw e;
   }
}

ctx:	ctx NEWLINE					# printExpr
	|	'assertion condition 'expr 	# condition
	|	'assertion trigger 'expr 	# trigger
	|	NEWLINE                     # blank
	;

expr:	expr op='<' num				# cLT
    |	expr op='<=' num			# cLE
    |	expr op='>' num				# cGT
    |	expr op='>=' num			# cGE        
	|	expr op='=' value			# cEQ
	|	expr op='!=' value			# cDIFF
	|	expr op='&' expr			# cAnd
	|	expr op='|' expr			# cOr
	|	VAR							# cVar
	|   '(' expr ')'                # cParens
	;

value:	num							# cNum
	|	BOOL						# cBool
	;

num:	INT							#cInt
	|	FLOAT						#cFloat
	;    

BOOL		: ('false'|'true') 					;
VAR     	: ('a'..'z'|'A'..'Z'|'_')+DIGIT*	;
INT			: DIGIT+							;
FLOAT		: DIGIT+'.'DIGIT* 					;
NEWLINE 	: [\r\n]+             				;
WS	        : (' '|'\t')+ -> skip 				;

fragment
DIGIT		: [0-9]							;