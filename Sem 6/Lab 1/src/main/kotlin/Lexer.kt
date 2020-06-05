import jdk.nashorn.api.tree.GotoTree
import java.lang.StringBuilder

class Lexer(fileName: String) {
    private var state = 0
    private var buffer: StringBuilder = StringBuilder("")
    val tokens: MutableList<Token> = mutableListOf()
    private val reader = CodeReader(fileName)

    private fun goto(state: Int, c: Char) {
        this.state = state
        buffer.append(c)
    }

    private fun justGo(state: Int) {
        this.state = state
    }

    private fun addToken(type: Token.Type) {
        tokens.add(Token(buffer.toString(), type))
        buffer.clear()
        state = 0
    }

    private fun operatorIfOk(c: Char) {
        when {
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    fun run() {

        // TODO: #"..."#
        // TODO: #something

        // TODO: Numbers
        // TODO: Strings and char literals

        var c = reader.next()
        while (c != 0.toChar()) {
            when (state) {
                -1 -> errorState(c)
                0 -> startState(c)
                1 -> slash(c)
                2 -> singleLineComment(c)
                3 -> beginMultilineComment(c)
                4 -> maybeEndOfMultilineComment(c)
                5 -> endOfMultilineComment(c)
                6 -> operator(c)
                7 -> equalsSign(c)
                8 -> doubleEquals(c)
                9 -> tripleEquals(c)
                11 -> operatorSlashEquals(c)
                12 -> roofSign(c)
                13 -> roofEqualsOperator(c)
                15 -> plus(c)
                16 -> plusAndPlusOrEquals(c)
                17 -> minus(c)
                19 -> minusAndSomething(c)
                22 -> exclamationMark(c)
                23 -> exclamationMarkAndEquals(c)
                24 -> exclamationMarkAndDoubleEquals(c)
                26 -> dot(c)
                27 -> dotDot(c)
                28 -> dotDotAndSomething(c)
                31 -> tilda(c)
                32 -> tildaAndSomething(c)
                34 -> percent(c)
                35 -> percentAndEquals(c)
                37 -> vertical(c)
                38 -> verticalAndSomething(c)
                40 -> star(c)
                41 -> starAndEquals(c)
                43 -> questionMark(c)
                44 -> questionMarkAndSomething(c)
                46 -> ampersand(c)
                47 -> ampersandAndSomething(c)
                48 -> ampersandAndRight(c)
                49 -> ampersandAndLeft(c)
                50 -> leftLeft(c)
                51 -> rightRight(c)
                53 -> leftLeftEquals(c)
                54 -> rightRightEquals(c)
                55 -> right(c)
                56 -> left(c)
                58 -> rightEquals(c)
                59 -> leftEquals(c)

                // Identifiers
                100 -> identifier(c)

                // Number Literals
                101 -> zeroDigit(c)
                102 -> binaryDigits(c)
                103 -> octalDigits(c)
                104 -> hexDigits(c)
                105 -> decimalDigits(c)
                106 -> decimalWithDot(c)
                107 -> decimalWithDotAndDecimals(c)
                108 -> decimalWithE(c)
                109 -> floatDecimalWithSign(c)
                110 -> floatDecimal(c)
                111 -> hexAndDot(c)
                112 -> hexFloat(c)
                113 -> hexAndP(c)
                114 -> hexAndPAndHex(c)
                115 -> hexAndPAndSymbol(c)
                116 -> hexDigitsFull(c)
                117 -> octalDigitsFull(c)
                118 -> binaryDigitsFull(c)
                119 -> floatLiteral(c)
                120 -> integerLiteral(c)

                // String Literals
                200 -> rawString(c)
                202 -> rawStringOne(c)
                203 -> rawStringTwo(c)
                204 -> rawStringThree(c)
                205 -> rawStringFour(c)
                206 -> rawStringFive(c)
                207 -> rawStringSix(c)
                208 -> stringLiteral(c)
                209 -> stringOne(c)
                210 -> stringTwo(c)
                211 -> singleCharOpen(c)
                212 -> singleCharBeforeClosed(c)
                213 -> singleCharAddSpecial(c)
                214 -> directive(c)
                215 -> basicString(c)
                216 -> basicStringTwo(c)
                217 -> basicStringThree(c)
                218 -> basicStringFour(c)
                219 -> basicStringFive(c)
                221 -> simpleBasicString(c)
                222 -> specialCharSimple(c)
                223 -> charLiteral(c)
                224 -> specialCharCompl(c)



                else -> {
                    print("Error state of $state. Please check scheme again.")
                }
            }

            c = reader.next()
        }
    }

    /**
     * STATE -1
     * Error State
     */
    private fun errorState(c: Char) {
        reader.stepBack()
        addToken(Token.Type.ERROR)
    }

    /**
     * STATE 0
     * Starting State
     * Buffer is empty
     */
    private fun startState(c: Char) {
        // TODO
        when {
            Character.isWhitespace(c) -> {
                tokens.add(Token(c.toString(), Token.Type.WHITESPACE))
                buffer.clear()
                state = 0
            }
            c == '0' -> goto(101, c)
            isDecimal(c) && c != '0' -> goto(105, c)
            c == '.' -> {
                tokens.add(Token(c.toString(), Token.Type.OPERATOR))
                buffer.clear()
                state = 0
            }
            isPartOfIdentifier(c) -> goto(100, c)
            isSeparator(c) -> {
                tokens.add(Token(c.toString(), Token.Type.SEPARATOR))
                buffer.clear()
                state = 0
            }
            c == '/' -> goto(1, c)
            c == '=' -> goto(7, c)
            c == '^' -> goto(12, c)
            c == '+' -> goto(15, c)
            c == '-' -> goto(17, c)
            c == '!' -> goto(22, c)
            c == '.' -> goto(26, c)
            c == '~' -> goto(31, c)
            c == '%' -> goto(34, c)
            c == '|' -> goto(37, c)
            c == '*' -> goto(40, c)
            c == '?' -> goto(43, c)
            c == '&' -> goto(46, c)
            c == '>' -> goto(55, c)
            c == '<' -> goto(56, c)
            c == '#' -> goto(200, c)
            c == '\'' -> goto(211, c)
            c == '"' -> goto(215, c)
            else -> goto(0, c)
        }
    }

    /**
     * STATE 100
     * IDENTIFIERS
     */
    private fun identifier(c: Char) {
        when {
            isPartOfIdentifier(c) -> goto(100, c)
            Character.isWhitespace(c) || isOperator(c) || isSeparator(c) -> {
                when {
                    isNil(buffer.toString()) -> addToken(Token.Type.LITERAL_NULL)
                    isBoolean(buffer.toString()) -> addToken(Token.Type.LITERAL_BOOLEAN)
                    isPrimitive(buffer.toString()) -> addToken(Token.Type.PRIMITIVE)
                    isKeyword(buffer.toString()) -> addToken(Token.Type.KEYWORD)
                    else -> addToken(Token.Type.IDENTIFIER)
                }
                reader.stepBack()
            }
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 101
     * 0 in buffer
     */
    private fun zeroDigit(c: Char) {
        when {
            c == 'b' -> goto(102, c)
            c == 'o' -> goto(103, c)
            c == 'x' -> goto(104, c)
            c == '.' -> goto(106, c)
            isDecimal(c) -> goto(105, c)
            isOperator(c) || isSeparator(c) || c == ' ' -> goto(120, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 102
     * 0b in buffer
     */
    private fun binaryDigits(c: Char) {
        when {
            c == '0' || c == '1' -> goto(118, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 103
     * 0o in buffer
     */
    private fun octalDigits(c: Char) {
        when {
            isOctal(c) -> goto(117, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 104
     * 0x in buffer
     */
    private fun hexDigits(c: Char) {
        when {
            isHex(c) -> goto(116, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 105
     * 0..9 in buffer
     */
    private fun decimalDigits(c: Char) {
        when {
            isDecimal(c) -> goto(105, c)
            c == '.' -> goto(106, c)
            isOperator(c) || isSeparator(c) || c == ' ' -> goto(120, c)
            c == 'e' || c == 'E' -> goto(108, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 106
     * 0..9. in buffer
     */
    private fun decimalWithDot(c: Char) {
        when {
            isDecimal(c) -> goto(107, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 107
     * 0..9.0..9 in buffer
     */
    private fun decimalWithDotAndDecimals(c: Char) {
        when {
            isDecimal(c) -> goto(107, c)
            isOperator(c) || isSeparator(c) || c == ' ' -> goto(119, c)
            c == 'e' || c == 'E' -> goto(108, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 108
     * decimal with e in buffer
     */
    private fun decimalWithE(c: Char) {
        when {
            isDecimal(c) -> goto(110, c)
            c == '+' || c == '-' -> goto(109, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 109
     * float decimal with something in buffer
     */
    private fun floatDecimalWithSign(c: Char) {
        when {
            isDecimal(c) -> goto(110, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 110
     * float decimal in buffer
     */
    private fun floatDecimal(c: Char) {
        when {
            isDecimal(c) -> goto(110, c)
            isOperator(c) || isSeparator(c) || c == ' ' -> goto(119, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 111
     * 0x.. . in buffer
     */
    private fun hexAndDot(c: Char) {
        when {
            isHex(c) -> goto(112, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 112
     * 0x... . . in buffer
     */
    private fun hexFloat(c: Char) {
        when {
            c == 'p' || c == 'P' -> goto(113, c)
            isOperator(c) || isSeparator(c) || c == ' ' -> goto(119, c)
            isHex(c) -> goto(112, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 113
     * 0x.. . .. pP in buffer
     */
    private fun hexAndP(c: Char) {
        when {
            c == '+' || c == '-' -> goto(115, c)
            isHex(c) -> goto(114, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 114
     * 0x.. . .. pP in buffer
     */
    private fun hexAndPAndHex(c: Char) {
        when {
            isHex(c) -> goto(114, c)
            isOperator(c) || isSeparator(c) || c == ' ' -> goto(119, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 115
     * 0x.. . .. pP+-.. in buffer
     */
    private fun hexAndPAndSymbol(c: Char) {
        when {
            isHex(c) -> goto(114, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 116
     * 0x.. in buffer
     */
    private fun hexDigitsFull(c: Char) {
        when {
            isHex(c) -> goto(116, c)
            c == '.' -> goto(111, c)
            c == 'p' || c == 'P' -> goto(113, c)
            isOperator(c) || isSeparator(c) || c == ' ' -> goto(119, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 117
     * 0o.. in buffer
     */
    private fun octalDigitsFull(c: Char) {
        when {
            isOctal(c) -> goto(117, c)
            isOperator(c) || isSeparator(c) || c == ' ' -> goto(120, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 118
     * 0b.. in buffer
     */
    private fun binaryDigitsFull(c: Char) {
        when {
            c == '0' || c == '1' -> goto(118, c)
            isOperator(c) || isSeparator(c) || c == ' ' -> goto(120, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 119
     * FLOAT LITERAL
     */
    private fun floatLiteral(c: Char) {
        reader.stepBack()
        reader.stepBack()
        tokens.add(Token(buffer.toString().substring(0, buffer.length), Token.Type.LITERAL_FLOAT))
        buffer.clear()
        state = 0
    }

    /**
     * STATE 120
     * INTEGER LITERAL
     */
    private fun integerLiteral(c: Char) {
        reader.stepBack()
        reader.stepBack()
        tokens.add(Token(buffer.toString().substring(0, buffer.length - 1), Token.Type.LITERAL_INT))
        buffer.clear()
        state = 0
    }

    /**
     * STATE 200
     * RAW COMMENT
     * # in buffer
     */
    private fun rawString(c: Char) {
        when {
            c == '"' -> goto(202, c)
            isPartOfIdentifier(c) -> goto(214, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 202
     * #" in buffer
     */
    private fun rawStringOne(c: Char) {
        when {
            c == '"' -> goto(203, c)
            c == '\n' -> goto(-1, c)
            else -> goto(209, c)
        }
    }

    /**
     * STATE 203
     * #"" in buffer
     */
    private fun rawStringTwo(c: Char) {
        when {
            c == '"' -> goto(204, c)
            c == '\n' -> goto(-1, c)
            else -> goto(209, c)
        }
    }

    /**
     * STATE 204
     * #""" and symbols in buffer
     */
    private fun rawStringThree(c: Char) {
        when {
            c == '"' -> goto(205, c)
            else -> goto(204, c)
        }
    }

    /**
     * STATE 205
     * #""" ... " in buffer
     */
    private fun rawStringFour(c: Char) {
        when {
            c == '"' -> goto(206, c)
            else -> goto(204, c)
        }
    }

    /**
     * STATE 206
     * #""" ... "" in buffer
     */
    private fun rawStringFive(c: Char) {
        when {
            c == '"' -> goto(207, c)
            else -> goto(204, c)
        }
    }

    /**
     * STATE 207
     * #""" ... """ in buffer
     */
    private fun rawStringSix(c: Char) {
        when {
            c == '#' -> goto(208, c)
            else -> goto(204, c)
        }
    }

    /**
     * STATE 208
     * #""" ... """# in buffer
     */
    private fun stringLiteral(c: Char) {
        if (isSeparator(c) || isOperator(c) || c == ' ') {
            reader.stepBack()
            addToken(Token.Type.LITERAL_STRING)
        }
        else {
            reader.stepBack()
            addToken((Token.Type.ERROR))
        }
    }

    /**
     * STATE 209
     * #" ... in string
     */
    private fun stringOne(c: Char) {
        when {
            c == '"' -> goto(210, c)
            c == '\n' -> goto(-1, c)
            else -> goto(209, c)
        }
    }

    /**
     * STATE 210
     * #" ... " in string
     */
    private fun stringTwo(c: Char) {
        when {
            c == '#' -> goto(208, c)
            c == '\n' -> goto(-1, c)
            else -> goto(208, c)
        }
    }

    /**
     * STATE 211
     * ' in buffer
     */
    private fun singleCharOpen(c: Char) {
        when {
            c == '\\' -> goto(213, c)
            c == '\n' -> goto(-1, c)
            else -> goto(212, c)
        }
    }

    /**
     * STATE 212
     * '. OR '\. in buffer
     */
    private fun singleCharBeforeClosed(c: Char) {
        when {
            c == '\'' -> goto(223, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 213
     * '\ in buffer
     */
    private fun singleCharAddSpecial(c: Char) {
        when {
            c == '\n' -> goto(-1, c)
            else -> goto(212, c)
        }
    }

    /**
     * STATE 214
     * #aA in buffer
     */
    private fun directive(c: Char) {
        when {
            isSeparator(c) || c == ' ' -> {
                reader.stepBack()
                if (isDirective(buffer.substring(1)))
                    addToken(Token.Type.DIRECTIVE)
                else
                    addToken(Token.Type.ERROR)
            }
            isPartOfIdentifier(c) -> goto(214, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 215
     * " in buffer
     */
    private fun basicString(c: Char) {
        when {
            c == '"' -> goto(216, c)
            c == '\\' -> goto(222, c)
            c == '\n' -> goto(-1, c)
            else -> goto(221, c)
        }
    }

    /**
     * STATE 216
     * "" in buffer
     */
    private fun basicStringTwo(c: Char) {
        when {
            c == '"' -> goto(217, c)
            c == '\\' -> goto(222, c)
            c == '\n' -> goto(-1, c)
            else -> goto(221, c)
        }
    }

    /**
     * STATE 217
     * """ in buffer
     */
    private fun basicStringThree(c: Char) {
        when {
            c == '"' -> goto(218, c)
            c == '\\' -> goto(224, c)
            else -> goto(217, c)
        }
    }

    /**
     * STATE 218
     * """ ... " in buffer
     */
    private fun basicStringFour(c: Char) {
        when {
            c == '"' -> goto(219, c)
            else -> goto(217, c)
        }
    }

    /**
     * STATE 219
     * """ ... "" in buffer
     */
    private fun basicStringFive(c: Char) {
        when {
            c == '"' -> goto(208, c)
            else -> goto(217, c)
        }
    }

    /**
     * STATE 221
     * " ... in buffer
     */
    private fun simpleBasicString(c: Char) {
        when {
            c == '"' -> goto(208, c)
            c == '\n' -> goto(-1, c)
            c == '\\' -> goto(222, c)
            else -> goto(221, c)
        }
    }

    /**
     * STATE 222
     * " .... \ in buffer
     */
    private fun specialCharSimple(c: Char) {
        when {
            c == '\n' -> goto(-1, c)
            else -> goto(221, c)
        }
    }

    /**
     * STATE 223
     * '.' OR '\.' in buffer
     */
    private fun charLiteral(c: Char) {
        reader.stepBack()
        addToken(Token.Type.LITERAL_CHAR)
    }

    /**
     * STATE 224
     * """ ... \ in buffer
     */
    private fun specialCharCompl(c: Char) {
        goto(217, c)
    }



    /**
     * STATE 1
     * / in buffer
     */
    private fun slash(c: Char) {
        when {
            c == '/' -> goto(2, c)
            c == '=' -> goto(11, c)
            c == '*' -> goto(3, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 2
     * // in buffer
     */
    private fun singleLineComment(c: Char) {
        when {
            c == '\n' -> goto(5, c)
            else -> goto(2, c)
        }
    }

    /**
     * STATE 3
     * Begin of comment
     * /\* in buffer
     */
    private fun beginMultilineComment(c: Char) {
        when {
            c == '*' -> goto(4, c)
            else -> goto(3, c)
        }
    }

    /**
     * STATE 4
     * Maybe end of multiline comment
     * / * ... * in buffer
     */
    private fun maybeEndOfMultilineComment(c: Char) {
        when {
            c == '/' -> goto(5, c)
            else -> goto(3, c)
        }
    }

    /**
     * STATE 5, 20
     * End of multiline comment OR End of single-line comment
     * / * .... * in buffer OR //, text and newline in buffer
     */
    private fun endOfMultilineComment(c: Char) {
        addToken(Token.Type.COMMENT)
    }

    /**
     * STATE 6, 10, 14, 18, 29
     * Operators
     * Operator and one char in buffer
     */
    private fun operator(c: Char) {
        reader.stepBack()
        reader.stepBack()
        buffer.setLength(buffer.length - 1)
        addToken(Token.Type.OPERATOR)
    }

    /**
     * STATE 7
     * Equals sign
     * = in buffer
     */
    private fun equalsSign(c: Char) {
        when {
            c == '=' -> goto(8, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 8
     * Double equals sign
     * == in buffer
     */
    private fun doubleEquals(c: Char) {
        when {
            c == '=' -> goto(9, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 9
     * Triple equals sign
     * === in buffer
     */
    private fun tripleEquals(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 11
     * Operator /=
     * /= in buffer
     */
    private fun operatorSlashEquals(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 12
     * Roof sign
     * ^ in buffer
     */
    private fun roofSign(c: Char) {
        when {
            c == '=' -> goto(13, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 13
     * ^= Operator
     * ^= in buffer
     */
    private fun roofEqualsOperator(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 15
     * + sign
     * + in buffer
     */
    private fun plus(c: Char) {
        when {
            c == '+' || c == '=' -> goto(16, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 16
     * ++ OR += operator
     * ++ OR += in buffer
     */
    private fun plusAndPlusOrEquals(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 17
     * Operator -
     * - in buffer
     */
    private fun minus(c: Char) {
        when {
            c == '-' || c == '>' || c == '=' -> goto(19, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 19
     * Operators --, -= and ->
     * -- OR -= OR -> in buffer
     */
    private fun minusAndSomething(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 22
     * Operator !
     * ! in buffer
     */
    private fun exclamationMark(c: Char) {
        when {
            c == '=' -> goto(23, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 23
     * Operator !=
     * != in buffer
     */
    private fun exclamationMarkAndEquals(c: Char) {
        when {
            c == '=' -> goto(24, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 24
     * Operator !==
     * !== in buffer
     */
    private fun exclamationMarkAndDoubleEquals(c: Char) {
        operatorIfOk(c)
    }


    /**
     * STATE 26
     * Operator .
     * . in buffer
     */
    private fun dot(c: Char) {
        when {
            c == '.' -> goto(27, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 27
     * Operator ..
     * .. in buffer
     */
    private fun dotDot(c: Char) {
        when {
            c == '.' || c == '<' -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 28
     * Operators ... OR ..<
     * ... OR ..< in buffer
     */
    private fun dotDotAndSomething(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 31
     * Operator ~
     * ~ in buffer
     */
    private fun tilda(c: Char) {
        when {
            c == '>' || c == '=' -> goto(32, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 32
     * Operators ~> OR ~=
     * ~> OR ~= in buffer
     */
    private fun tildaAndSomething(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 34
     * Operator %
     * % in buffer
     */
    private fun percent(c: Char) {
        when {
            c == '=' -> goto(35, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 35
     * Operator %=
     * %= in buffer
     */
    private fun percentAndEquals(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 37
     * Operator |
     * | in buffer
     */
    private fun vertical(c: Char) {
        when {
            c == '|' || c == '=' -> goto(38, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 38
     * Operators || OR |=
     * || OR |= in buffer
     */
    private fun verticalAndSomething(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 40
     * Operator *
     * * in buffer
     */
    private fun star(c: Char) {
        when {
            c == '=' -> goto(41, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 41
     * Operator *=
     * *= in buffer
     */
    private fun starAndEquals(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 43
     * Operator ?
     * ? in buffer
     */
    private fun questionMark(c: Char) {
        when {
            c == '.' || c == '?' -> goto(44, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 44
     * Operators ?. OR ??
     * ?. OR ?? in buffer
     */
    private fun questionMarkAndSomething(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 46
     * Operator &
     * & in buffer
     */
    private fun ampersand(c: Char) {
        when {
            c == '&' || c == '=' || c == '+'
                    || c == '-' || c == '%'
                    || c == '*' || c == '/' -> goto(47, c)
            c == '>' -> goto(48, c)
            c == '<' -> goto(49, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 47
     * Operators && OR &= OR &+ OR &- OR &% OR &* OR &/
     * && OR &= OR &+ OR &- OR &% OR &* OR &/ in buffer
     */
    private fun ampersandAndSomething(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 48
     * Part of operator &>
     * &> in buffer
     */
    private fun ampersandAndRight(c: Char) {
        when {
            c == '>' -> goto(50, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 49
     * Part of operator &<
     * &< in buffer
     */
    private fun ampersandAndLeft(c: Char) {
        when {
            c == '<' -> goto(51, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 50
     * Operator &<< OR <<
     * &<< OR << in buffer
     */
    private fun leftLeft(c: Char) {
        when {
            c == '=' -> goto(54, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 51
     * Operator &>> OR >>
     * &>> OR >> in buffer
     */
    private fun rightRight(c: Char) {
        when {
            c == '=' -> goto(55, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 53
     * Operator &<<= OR <<=
     * &<<= OR <<= in buffer
     */
    private fun leftLeftEquals(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 54
     * Operator &>>= OR >>=
     * &>>= OR >>= in buffer
     */
    private fun rightRightEquals(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 55
     * Operator >
     * > in buffer
     */
    private fun right(c: Char) {
        when {
            c == '>' -> goto(50, c)
            c == '=' -> goto(58, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 56
     * Operator <
     * < in buffer
     */
    private fun left(c: Char) {
        when {
            c == '>' -> goto(50, c)
            c == '=' -> goto(59, c)
            isRegular(c) -> goto(6, c)
            else -> goto(-1, c)
        }
    }

    /**
     * STATE 58
     * Operator >=
     * >= in buffer
     */
    private fun rightEquals(c: Char) {
        operatorIfOk(c)
    }

    /**
     * STATE 59
     * Operator <=
     * <= in buffer
     */
    private fun leftEquals(c: Char) {
        operatorIfOk(c)
    }


}