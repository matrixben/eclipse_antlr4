grammar JSON;

json : object
     | array
     ;

object : '{' pair (',' pair)* '}'     # AnObject
       | '{' '}'                      # EmptyObject
       ;
       
pair : STRING ':' value ;

array : '[' value (',' value)* ']'    # ArrayOfValues
      | '[' ']'                       # EmptyArray
      ;

value : STRING                        # String
      | NUMBER                        # Atom
      | object                        # ObjectValue
      | array                         # ArrayValue
      | 'true'                        # Atom
      | 'false'                       # Atom
      | 'null'                        # Atom
      ;
//词法规则
STRING : '"' (ESC | ~["\\])* '"' ;  //~["\\]一切字符除了双引号和反斜杠

NUMBER : '-'? INT '.' INT EXP?  //负号，整数，小数点，整数，科学计数法e,如-1.2e-9
       | '-'? INT EXP?          //无小数点，如2e4
       | '-'? INT
       ;

//fragment表示此词法规则只能给其他词法规则使用，不能用于语法规则
fragment
ESC : '\\' (["\\/bfnrt] | UNICODE) ;
fragment
UNICODE : 'u' HEX HEX HEX HEX ;
fragment
HEX : [0-9a-fA-F] ;

fragment INT : '0' | [1-9] [0-9]* ;  //除了0以外的数字不能以0开头，多位数的最高位只能是1-9
fragment EXP : [Ee] '-'? INT ;

WS : [ \t\r\n]+ -> skip ;
