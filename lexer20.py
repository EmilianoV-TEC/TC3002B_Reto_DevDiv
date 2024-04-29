from ply import lex

# List of token names
tokens = (
    'ID',
    'INT_LITERAL',
    'FLOAT_LITERAL',
    'STRING_LITERAL',
    'CHAR_LITERAL',
    'PLUS',
    'MINUS',
    'TIMES',
    'DIVIDE',
    'MOD',
    'LPAREN',
    'RPAREN',
    'LBRACE',
    'RBRACE',
    'SEMICOLON',
    'COMMA',
    'DOT',
    'ASSIGN',
    'EQ',
    'NEQ',
    'GT',
    'LT',
    'GE',
    'LE',
    'NOT',
    'AND',
    'OR',
    'INC',
    'DEC',
    'BIT_AND',
    'BIT_OR',
    'BIT_XOR',
    'BIT_NOT',
    'LEFT_SHIFT',
    'RIGHT_SHIFT',
    'UNSIGNED_RIGHT_SHIFT',
    'INCREMENT',
    'DECREMENT',
    'TERNARY',
    'COLON',
    'ARROW',
    'AT',
    'ELLIPSIS',
    'INTEGER_TYPE_SUFFIX',
    'FLOAT_TYPE_SUFFIX',
    'NULL_LITERAL',
    'TRUE_LITERAL',
    'FALSE_LITERAL',
    'BREAK',
    'CASE',
    'CATCH',
    'CLASS',
    'CONST',
    'CONTINUE',
    'DEFAULT',
    'DO',
    'ELSE',
    'ENUM',
    'EXTENDS',
    'FINALLY',
    'FINAL',
    'FOR',
    'IF',
    'INSTANCEOF',
    'NEW',
    'RETURN',
    'STATIC',
    'SWITCH',
    'THIS',
    'THROW',
    'TRY',
    'VOID',
    'WHILE',
    'PACKAGE',
    'IMPORT',
    'SUPER',
)

# Regular expression rules for simple tokens
t_PLUS = r'\+'
t_MINUS = r'-'
t_TIMES = r'\*'
t_DIVIDE = r'/'
t_MOD = r'%'
t_LPAREN = r'\('
t_RPAREN = r'\)'
t_LBRACE = r'\{'
t_RBRACE = r'\}'
t_SEMICOLON = r';'
t_COMMA = r','
t_DOT = r'\.'
t_ASSIGN = r'='
t_EQ = r'=='
t_NEQ = r'!='
t_GT = r'>'
t_LT = r'<'
t_GE = r'>='
t_LE = r'<='
t_NOT = r'!'
t_AND = r'&&'
t_OR = r'\|\|'
t_INC = r'\+\+'
t_DEC = r'--'
t_BIT_AND = r'&'
t_BIT_OR = r'\|'
t_BIT_XOR = r'\^'
t_BIT_NOT = r'~'
t_LEFT_SHIFT = r'<<'
t_RIGHT_SHIFT = r'>>'
t_UNSIGNED_RIGHT_SHIFT = r'>>>'
t_INCREMENT = r'\+='
t_DECREMENT = r'-='
t_TERNARY = r'\?'
t_COLON = r':'
t_ARROW = r'->'
t_AT = r'@'
t_ELLIPSIS = r'\.\.\.'
t_INTEGER_TYPE_SUFFIX = r'[lL]'
t_FLOAT_TYPE_SUFFIX = r'[fFdD]'
t_NULL_LITERAL = r'null'
t_TRUE_LITERAL = r'true'
t_FALSE_LITERAL = r'false'
t_BREAK = r'break'
t_CASE = r'case'
t_CATCH = r'catch'
t_CLASS = r'class'
t_CONST = r'const'
t_CONTINUE = r'continue'
t_DEFAULT = r'default'
t_DO = r'do'
t_ELSE = r'else'
t_ENUM = r'enum'
t_EXTENDS = r'extends'
t_FINALLY = r'finally'
t_FINAL = r'final'
t_FOR = r'for'
t_IF = r'if'
t_INSTANCEOF = r'instanceof'
t_NEW = r'new'
t_RETURN = r'return'
t_STATIC = r'static'
t_SWITCH = r'switch'
t_THIS = r'this'
t_THROW = r'throw'
t_TRY = r'try'
t_VOID = r'void'
t_WHILE = r'while'
t_PACKAGE = r'package'
t_IMPORT = r'import'
t_SUPER = r'super'

# Define a rule so we can track line numbers
def t_newline(t):
    r'\n+'
    t.lexer.lineno += len(t.value)

# A regular expression rule with some action code
def t_ID(t):
    r'[a-zA-Z_][a-zA-Z0-9_]*'
    return t

def t_FLOAT_LITERAL(t):
    r'\d+\.\d+'
    t.value = float(t.value)
    return t

def t_INT_LITERAL(t):
    r'\d+'
    t.value = int(t.value)
    return t

def t_STRING_LITERAL(t):
    r'\"([^\\\n]|(\\.))*?\"'
    t.value = t.value[1:-1]  # Remove quotes
    return t

def t_CHAR_LITERAL(t):
    r'\'([^\\\n]|(\\.))*?\''
    t.value = t.value[1:-1]  # Remove quotes
    return t

# A string containing ignored characters (spaces and tabs)
t_ignore = ' \t'

# Error handling rule
def t_error(t):
    print("Illegal character '%s'" % t.value[0])
    t.lexer.skip(1)

# Build the lexer
lexer = lex.lex()

# Test the lexer
data = '''
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
'''

lexer.input(data)

# Tokenize
for tok in lexer:
    print(tok)
