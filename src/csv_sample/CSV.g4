grammar CSV;

file   : header row+ ;
header : row ;
row    : field (',' field)* '\r'? '\n' ; //?表示可选项，用于匹配windows下的换行符
//空白匹配null
field
    : TEXT          # text
    | STRING        # string
    |               # empty
    ;
//词法规则大写
TEXT   : ~[,\n\r"]+ ;
//两个双引号""表示转义字符，~表示任意字符序列
STRING : '"' ('""'|~'"')* '"' ;