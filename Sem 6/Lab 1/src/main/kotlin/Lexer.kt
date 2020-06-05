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
        // TODO
        addToken(Token.Type.ERROR)
    }

    /**
     * STATE 0
     * Starting State
     * Buffer is empty
     */
    private fun startState(c: Char) {
        // TODO
        when (c) {
            '/' -> goto(1, c)
            '=' -> goto(7, c)
            '^' -> goto(12, c)
            '+' -> goto(15, c)
            '-' -> goto(17, c)
            '!' -> goto(22, c)
            '.' -> goto(26, c)
            '~' -> goto(31, c)
            '%' -> goto(34, c)
            '|' -> goto(37, c)
            '*' -> goto(40, c)
            '?' -> goto(43, c)
            '&' -> goto(46, c)
            '>' -> goto(55, c)
            '<' -> goto(56, c)
            else -> goto(0, c)
        }
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