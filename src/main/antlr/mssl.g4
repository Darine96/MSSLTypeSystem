grammar mssl;
//Parser

program: declaration* block EOF                                                         #Prog
       ;
declaration: FN IDENTIFIER LPAR (params)? (signals)? RPAR Sub GT signature block                   #Declaration_function;

params: MUT IDENTIFIER COLON signature ( COMMA MUT IDENTIFIER COLON signature)* #ParamsFunc;
signals: (SEMIC IDENTIFIER (COMMA IDENTIFIER)* )                                       #SignalsFunc;
signature: Unit                                                                         #SigUnit
         | Int                                                                          #SigInt
         | Bool                                                                         #SigBool
         | Box LT signature GT                                                          #SigBox
         |Trc LT signature GT                                                           #SigTrc
         |Clone LT signature GT                                                         #SigClone
         ;

expr: value                                                                             #ExpVal
   // | IDENTIFIER                                                                        #ExpIdentifier
    | expr Dot INTEGER                                                                  #ExpIndex
    | (IDENTIFIER | Mul expr ) Dot Clone                                                #ExpClone
    | (IDENTIFIER | Mul expr )                                                          #ExpDeref
    | Ref expr                                                                          #ExpSharedRef
    | Ref MUT expr                                                                      #ExpMutableRef
    | Box LPAR expr RPAR                                                                #ExpBox
    | Trc LPAR expr RPAR                                                                #ExpTrc
    | LPAR (expr(COMMA expr)*)?RPAR                                                     #ExpTuple
    | Spawn LPAR IDENTIFIER LPAR (expr (COMMA expr)*)? (signals)? RPAR RPAR             #ExpInvoke
    | Cooperate                                                                         #ExpCooperate
   // | expr  (SEMIC expr SEMIC?)                         #ExpSequence
    | block                                                                             #ExpBlock
    | PRINT LPAR ((DoubleQuote IDENTIFIER DoubleQuote) | (Mul* IDENTIFIER) | ((DoubleQuote IDENTIFIER DoubleQuote PLUS IDENTIFIER)(PLUS DoubleQuote IDENTIFIER DoubleQuote)*))  RPAR        #ExpPrint
    ;

type_expression: Int                                                                    #TypInt
               | Bool                                                                   #TypBoolean
               ;

block: LBRACE instructions RBRACE                                                       #StmtBlock;

instructions: instruction*                                                              #InstSequence
            ;

instruction: declVar                                                                    #InstLet
           | expr EQ expr SEMIC                                                         #InstAssignment
           | expr SEMIC                                                                 #InstExpr
           | Emit LPAR IDENTIFIER RPAR  SEMIC                                                #InstEmit
           | When LPAR IDENTIFIER RPAR block                                             #InstWhen
           | Watch LPAR IDENTIFIER RPAR block                                            #InstWatch
           | Sig IDENTIFIER SEMIC                                                       #InstSig
//           |expr SEMIC (SEMIC expr)*                          # InstSequence
           | block                                                                      #InstBlock
           ;

declVar: LET MUT IDENTIFIER EQ expr SEMIC;


value: INTEGER                                                                          #Int
     | BOOLEAN                                                                          #BOOL
     ;



//Lexer
INTEGER: '0' | [1-9] [0-9]*;
BOOLEAN: 'true'
       | 'false'
       ;
Int: 'int';
Bool: 'bool';
Unit: 'unit';
EQ : '=';
SEMIC : ';';
COMMA: ',';
COLON: ':';
LBRACE : '{';
RBRACE : '}';
LPAR :'(';
RPAR : ')';
LT : '<';
GT : '>';
DoubleQuote: '"';
Sub: '-';
PLUS: '+';
FN: 'fn';
MAIN: 'main';
LET: 'let';
MUT: 'mut';
Box: 'box';
Trc: 'trc';
Sig: 'Sig';
Emit: 'emit';
When: 'when';
Watch: 'watch';
Clone: 'clone';
Spawn: 'spawn';
Cooperate: 'cooperate';
PRINT: 'print!';
Mul: '*';
Ref: '&';
Dot:'.';
IDENTIFIER: VALID_ID_START VALID_ID_CHAR*;
VALID_ID_START
   : ('a' .. 'z') | ('A' .. 'Z') | '_'
   ;
VALID_ID_CHAR
   : VALID_ID_START | ('0' .. '9')
   ;


WS: [ \t\r\n]+ -> skip;
OC_COMMENT : '/*' .*? '*/' -> skip;
SL_COMMENT : '//' .*? '\n' -> skip;
