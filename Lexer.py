from enum import IntEnum

class Tag(IntEnum):
	EOF = 65535
	ERROR = 65534
	## Operators ##
	GEQ = 258
	LEQ = 259
	NEQ = 260
	ASSIGN = 261
	## REGULAR EXPRESSIONS ##
	ID = 358
	NUMBER = 359
	STRING = 360
	TRUE = 361
	FALSE = 362
	## ADD THE MISSING RESERVED WORDS, JUST FOLLOW THE NUMBER SEQUENCE ##
	VAR = 457
	FORWARD = 458
	BACKWARD = 459
	RIGHT = 460
	LEFT = 461
	PENUP = 462
	PENDOWN = 463
	REPEAT = 464
	IF = 465
	IFELSE = 466
	AND = 467
	OR = 468
	MAKE = 469
	STOP = 470
	PRINT = 471
	MOD = 472
	SETX = 473
	SETY = 474
	SETXY = 475
	CIRCLE = 476
	CLEAR = 477
	ARC = 478
	COLOR = 479
	PENWIDTH = 480

class Token:
	__tag = Tag.EOF
	__value = None

	def __init__(self, tagId, val = None):
		self.__tag = tagId
		self.__value = val

	def getTag(self):
		return self.__tag

	def getValue(self):
		return self.__value

	def __str__(self):
		if self.__tag == Tag.GEQ:
			return "'>='"
		elif self.__tag == Tag.LEQ:
			return "'<='"
		elif self.__tag == Tag.NEQ:
			return "'<>'"
		elif self.__tag == Tag.ASSIGN:
			return "':='"
		elif self.__tag == Tag.TRUE:
			return "'#t'"
		elif self.__tag == Tag.FALSE:
			return "'#f'"
		elif self.__tag == Tag.NUMBER:
			return "numeric constant"
		elif self.__tag == Tag.ID:
			return "'" + str(self.__value) + "'"
		elif self.__tag >= Tag.VAR and self.__tag <= Tag.MOD:
			return "'" +  str(self.__value).lower() + "'"
		elif self.__tag == Tag.STRING:
			return "string constant"
		else:
			return "'" + chr(self.__tag) + "'"
			return "'" + chr(self.__tag) + "'"

class Lexer:
	__peek = ' '
	__words = {}
	__input = None

	def __init__(self, filepath):
		self.__input = open(filepath, "r")
		self.__peek = ' '

		self.__words["VAR"] = Token(Tag.VAR, "VAR")
		self.__words["FORWARD"] = Token(Tag.FORWARD, "FORWARD")
		self.__words["PRINT"] = Token(Tag.PRINT, "PRINT")
		self.__words["FD"] = Token(Tag.FORWARD, "FORWARD")
		self.__words["BACKWARD"] = Token(Tag.BACKWARD, "BACKWARD")
		self.__words["BD"] = Token(Tag.BACKWARD, "BACKWARD")
		self.__words["RIGHT"] = Token(Tag.BACKWARD, "BACKWARD")
		self.__words["RT"] = Token(Tag.RIGHT, "RIGHT")
		self.__words["RIGHT"] = Token(Tag.RIGHT, "RIGHT")
		self.__words["LEFT"] = Token(Tag.LEFT, "LEFT")
		self.__words["LT"] = Token(Tag.LEFT, "LEFT")
		self.__words["PENUP"] = Token(Tag.PENUP, "PENUP")
		self.__words["PENDOWN"] = Token(Tag.PENDOWN, "PENDOWN")
		self.__words["REPEAT"] = Token(Tag.REPEAT, "REPEAT")
		self.__words["IF"] = Token(Tag.IF, "IF")
		self.__words["IFELSE"] = Token(Tag.IFELSE, "IFELSE")
		self.__words["AND"] = Token(Tag.AND, "AND")
		self.__words["OR"] = Token(Tag.OR, "OR")
		self.__words["MAKE"] = Token(Tag.MAKE, "MAKE")
		self.__words["MOD"] = Token(Tag.BACKWARD, "MOD")
		self.__words["STOP"] = Token(Tag.STOP, "STOP")
		self.__words["SETX"] = Token(Tag.SETX, "SETX")
		self.__words["SETY"] = Token(Tag.SETY, "SETY")
		self.__words["SETXY"] = Token(Tag.CIRCLE, "CIRCLE")
		self.__words["CLEAR"] = Token(Tag.CLEAR, "CLEAR")
		self.__words["ARC"] = Token(Tag.ARC, "ARC")
		self.__words["COLOR"] = Token(Tag.COLOR, "COLOR")
		self.__words["PENWIDTH"] = Token(Tag.PENWIDTH, "PENWIDTH")
		## ADD THE REST RESERVED WORDS, REMEMBER THAT SOME RESERVER WORDS HAVE THE SAME TAG ##

	def read(self):
		self.__peek = self.__input.read(1)

	def readch(self, c):
		self.read()
		if self.__peek != c:
			return False

		self.__peek = ' '
		return True

	def __skipSpaces(self):
		while True:
			if self.__peek == ' ' or self.__peek == '\t' or self.__peek == '\r' or self.__peek == '\n':
				self.read()
			else:
				break

	def scan(self):
		self.__skipSpaces()
		if self.__peek == '%':
			while self.__peek != '\n':
				self.read()
		self.__skipSpaces()
		## ADD CODE TO SKIP COMMENTS HERE ##

		if self.__peek == '<':
			if self.readch('='):
				return Token(Tag.LEQ, "<=")
			elif self.readch('>'):
				return Token(Tag.NEQ, "<>")
			else:
				return Token(ord('<'))
		elif self.__peek == '>':
			if self.readch('='):
				return Token(Tag.GEQ, ">=")
			else:
				return Token(ord('>'))
		elif self.__peek == '#':
			if self.readch('t'):
				return Token(Tag.TRUE, "#t")
			elif self.readch('f'):
				return Token(Tag.FALSE, "#f")
			else:
				return Token(ord('#'))
		elif self.__peek == ':':
			if self.readch('='):
				#print("reading :=")
				return Token(Tag.ASSIGN, ":=")
			else:
				return Token(ord(':'))

		if self.__peek  == '"':
			val = ""
			while True:
				val = val + self.__peek
				self.read()
				if self.__peek == '"':
					break

			val = val + self.__peek
			self.read()
			return Token(Tag.STRING, val)

		if self.__peek.isdigit():
			val = 0
			dec = 0
			while True:
				val = (val * 10) + int(self.__peek)
				self.read()
				if not(self.__peek.isdigit()):
					break
			if self.__peek == '.':
				self.read()
				while True:
					dec = (dec / 10) + int(self.__peek)
					self.read()
					if not(self.__peek.isdigit()):
						break
			return Token(Tag.NUMBER, val+dec)

		if self.__peek.isalpha():
			val = ""
			while True:
				val = val + self.__peek.upper()
				self.read()
				if not(self.__peek.isalnum()):
					break

			if val in self.__words:
				return self.__words[val]

			w = Token(Tag.ID, val)
			self.__words[val] = Token(Tag.ID, val)
			return w

		if not(self.__peek):
			return Token(Tag.EOF)

		token = Token(ord(self.__peek))
		self.__peek = ' '
		return token