import Token.Type.*

class Lexer(fileName: String) {
    private var state = 0
    private var buffer: StringBuilder = StringBuilder("")
    val tokens: MutableList<Token> = mutableListOf()
    private val reader = CodeReader(fileName)

    private fun addToken(type: Token.Type) {
        tokens.add(Token(buffer.toString(), type))
        buffer.clear()
        state = 0
    }

    private fun stepBackAndAddToken(type: Token.Type) {
        reader.stepBack()
        tokens.add(Token(buffer.substring(0, buffer.length - 1), type))
        buffer.clear()
        state = 0
    }

    private fun goto(state: Int, c: Char) {
        this.state = state
//        buffer.append(c)
    }

    private fun goto(state: Int) {
        this.state = state
    }

    fun run() {
        var c = reader.next()
        while (c != 0.toChar()) {
            buffer.append(c)
            when (state) {
                0 -> state0(c)
                1 -> state1(c)
                2 -> state2(c)
                3 -> state3(c)
                4 -> state4(c)
                5 -> state5(c)
                6 -> state6(c)
                7 -> state7(c)
                8 -> state8(c)
                9 -> state9(c)
                10 -> state10(c)
                11 -> state11(c)
                12 -> state12(c)
                13 -> state13(c)
                14 -> state14(c)
                15 -> state15(c)
                16 -> state16(c)
                17 -> state17(c)
                18 -> state18(c)
                19 -> state19(c)
                20 -> state20(c)
                21 -> state21(c)
                22 -> state22(c)
                23 -> state23(c)
                24 -> state24(c)
                25 -> state25(c)
                26 -> state26(c)
                27 -> state27(c)
                28 -> state28(c)
                29 -> state29(c)
                30 -> state30(c)
                31 -> state31(c)
                32 -> state32(c)
                33 -> state33(c)
                35 -> state35(c)
                36 -> state36(c)
                37 -> state37(c)
                38 -> state38(c)
                39 -> state39(c)
                40 -> state40(c)
                41 -> state41(c)
                42 -> state42(c)
                43 -> state43(c)
                44 -> state44(c)
                46 -> state46(c)
                47 -> state47(c)
                48 -> state48(c)
                49 -> state49(c)
                50 -> state50(c)
                52 -> state52(c)
                53 -> state53(c)
                54 -> state54(c)
                55 -> state55(c)
                56 -> state56(c)
                57 -> state57(c)
                58 -> state58(c)
                59 -> state59(c)
                60 -> state60(c)
                61 -> state61(c)
                62 -> state62(c)
                63 -> state63(c)
                64 -> state64(c)
                65 -> state65(c)
                66 -> state66(c)
                67 -> state67(c)
                68 -> state68(c)

            }
            c = reader.next()
        }
    }

    private fun state0(c: Char) {
        when {
            isWhitespace(c) -> addToken(WHITESPACE)
            c == '/' -> goto(1)
            c == '!' || c == '=' -> goto(6)
            c == '+' -> goto(8)
            c == '&' -> goto(9)
            c == '-' -> goto(10)
            c == '?' -> goto(11)
            c == '*' -> goto(12)
            c == '%' -> goto(13)
            c == '~' -> goto(15)
            c == '>' -> goto(17)
            c == '<' -> goto(18)
            c == '.' -> goto(23)
            c == '\'' -> goto(25)
            c == '@' -> goto(28)
            c == '"' -> goto(29)
            c == '#' -> goto(38)
            c == '0' -> goto(49)
            isDecimal(c) && c != '0' -> goto(53)
            isValidChar(c) -> goto(67)
            isSeparator(c) -> addToken(SEPARATOR)
            isOperator(c) -> addToken(OPERATOR)
        }
    }

    private fun state1(c: Char) {
        when {
            c == '/' -> goto(2)
            c == '=' -> goto(3)
            c == '*' -> goto(4)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state2(c: Char) {
        when {
            isNewLine(c) -> stepBackAndAddToken(COMMENT)
            else -> goto(2)
        }
    }

    private fun state3(c: Char) {
        when {
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state4(c: Char) {
        when {
            c == '*' -> goto(5)
            else -> goto(4)
        }
    }

    private fun state5(c: Char) {
        when {
            c == '/' -> addToken(COMMENT)
            else -> goto(4)
        }
    }

    private fun state6(c: Char) {
        when {
            c == '=' -> goto(7)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state7(c: Char) {
        when {
            c == '=' -> goto(3)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state8(c: Char) {
        when {
            c == '+' || c == '=' -> goto(3)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state9(c: Char) {
        when {
            c == '=' || c == '&' -> goto(3)
            c == '+' -> goto(8)
            c == '-' -> goto(10)
            c == '*' -> goto(12)
            c == '%' -> goto(13)
            c == '>' -> goto(21)
            c == '<' -> goto(22)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state10(c: Char) {
        when {
            c == '-' || c == '=' || c == '>' -> goto(3)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state11(c: Char) {
        when {
            c == '?' || c == '.' -> goto(3)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state12(c: Char) {
        when {
            c == '=' -> goto(3)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state13(c: Char) {
        when {
            c == '=' -> goto(3)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state14(c: Char) {
        when {
            c == '=' -> goto(3)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state15(c: Char) {
        when {
            c == '>' || c == '=' -> goto(3)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state16(c: Char) {
        when {
            c == '=' || c == '|' -> goto(3)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state17(c: Char) {
        when {
            c == '>' -> goto(19)
            c == '=' -> goto(3)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state18(c: Char) {
        when {
            c == '<' -> goto(20)
            c == '=' -> goto(3)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }


    private fun state19(c: Char) {
        when {
            c == '=' -> goto(3)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state20(c: Char) {
        when {
            c == '-' -> goto(3)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state21(c: Char) {
        when {
            c == '>' -> goto(19)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state22(c: Char) {
        when {
            c == '<' -> goto(19)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state23(c: Char) {
        when {
            c == '.' -> goto(24)
            isRegular(c) -> stepBackAndAddToken(OPERATOR)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state24(c: Char) {
        when {
            c == '.' || c == '<' -> goto(3)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state25(c: Char) {
        when {
            c == '\\' -> goto(27)
            isNewLine(c) -> stepBackAndAddToken(ERROR)
            else -> goto(26)
        }
    }
    
    private fun state26(c: Char) {
        when {
            c == '\'' -> addToken(LITERAL_CHAR)
            else -> stepBackAndAddToken(ERROR)
        }
    }
    
    private fun state27(c: Char) {
        when {
            isNewLine(c) || c == '\\' -> stepBackAndAddToken(ERROR)
            else -> goto(26)
        }
    }

    private fun state28(c: Char) {
        when {
            isWhitespace(c) || isSeparator(c) || isOperator(c) -> {
                stepBackAndAddToken(INSTRUCTION)
            }
            isValidChar(c) -> goto(28)
            else -> stepBackAndAddToken(ERROR)
        }
    }
    
    private fun state29(c: Char) {
        when {
            c == '"' -> goto(30)
            isNewLine(c) -> stepBackAndAddToken(ERROR)
            c == '\\' -> goto(36)
            else -> goto(35)
        }
    }
    
    private fun state30(c: Char) {
        when {
            c == '"' -> goto(31)
            c == '\\' -> goto(36)
            isRegular(c) -> stepBackAndAddToken(LITERAL_STRING)
            else -> stepBackAndAddToken(ERROR)
        }
    }
    
    private fun state31(c: Char) {
        when {
            c == '"' -> goto(32)
            c == '\\' -> goto(37)
            else -> goto(31)
        }
    }
    
    private fun state32(c: Char) {
        when {
            c == '"' -> goto(33)
            c == '\\' -> goto(37)
            else -> goto(31)
        }
    }
    
    private fun state33(c: Char) {
        when {
            c == '"' -> addToken(LITERAL_STRING)
            c == '\\' -> goto(37)
            else -> goto(31)
        }
    }
    
    private fun state35(c: Char) {
        when {
            isNewLine(c) -> stepBackAndAddToken(ERROR)
            c == '\\' -> goto(36)
            c == '"' -> addToken(LITERAL_STRING)
            else -> goto(35)
        }
    }
    
    private fun state36(c: Char) {
        when {
            isWhitespace(c) -> stepBackAndAddToken(ERROR)
            else -> goto(35)
        }
    }
    
    private fun state37(c: Char) {
        when {
            isWhitespace(c) -> stepBackAndAddToken(ERROR)
            else -> goto(31)
        }
    }
    
    private fun state38(c: Char) {
        when {
            c == '"' -> goto(39)
            isValidChar(c) -> goto(48)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state39(c: Char) {
        when {
            c == '"' -> goto(40)
            isNewLine(c) -> stepBackAndAddToken(ERROR)
            else -> goto(46)
        }
    }

    private fun state40(c: Char) {
        when {
            c == '"' -> goto(41)
            c == '#' -> addToken(LITERAL_STRING)
            isNewLine(c) -> stepBackAndAddToken(ERROR)
            else -> goto(46)
        }
    }

    private fun state41(c: Char) {
        when {
            c == '"' -> goto(42)
            else -> goto(41)
        }
    }

    private fun state42(c: Char) {
        when {
            c == '"' -> goto(43)
            else -> goto(41)
        }
    }

    private fun state43(c: Char) {
        when {
            c == '"' -> goto(44)
            else -> goto(41)
        }
    }

    private fun state44(c: Char) {
        when {
            c == '#' -> addToken(LITERAL_STRING)
            else -> goto(41)
        }
    }

    private fun state46(c: Char) {
        when {
            c == '"' -> goto(47)
            isNewLine(c) -> stepBackAndAddToken(ERROR)
            else -> goto(46)
        }
    }

    private fun state47(c: Char) {
        when {
            c == '#' -> addToken(LITERAL_STRING)
            isNewLine(c) -> stepBackAndAddToken(ERROR)
            else -> goto(46)
        }
    }

    private fun state48(c: Char) {
        when {
            isWhitespace(c) || isSeparator(c) || isOperator(c) -> {
                if (isDirective(buffer.substring(1, buffer.length - 1)))
                    stepBackAndAddToken(DIRECTIVE)
                else
                    stepBackAndAddToken(ERROR)
            }
            isValidChar(c) -> goto(48)
        }
    }

    private fun state49(c: Char) {
        when {
            c == 'b' -> goto(50)
            c == 'o' -> goto(51)
            c == 'x' -> goto(52)
            c == '.' -> goto(61)
            isDecimal(c) -> goto(53)
            isSeparator(c) || isWhitespace(c) || isOperator(c) -> stepBackAndAddToken(LITERAL_INT)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state50(c: Char) {
        when {
            c == '0' || c == '1' -> goto(66)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state52(c: Char) {
        when {
            isHex(c) -> goto(55)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state53(c: Char) {
        when {
            isDecimal(c) -> goto(53)
            c == '.' -> goto(61)
            c == 'e' || c == 'E' -> goto(63)
            isSeparator(c) || isWhitespace(c) || isOperator(c) -> stepBackAndAddToken(LITERAL_INT)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state54(c: Char) {
        when {
            isOctal(c) -> goto(54)
            isSeparator(c) || isWhitespace(c) || isOperator(c) -> stepBackAndAddToken(LITERAL_INT)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state55(c: Char) {
        when {
            isHex(c) -> goto(55)
            c == '.' -> goto(56)
            c == 'p' || c == 'P' -> goto(58)
            isSeparator(c) || isWhitespace(c) || isOperator(c) -> stepBackAndAddToken(LITERAL_INT)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state56(c: Char) {
        when {
            isHex(c) -> goto(57)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state57(c: Char) {
        when {
            isHex(c) -> goto(57)
            c == 'p' || c == 'P' -> goto(58)
            isSeparator(c) || isWhitespace(c) || isOperator(c) -> stepBackAndAddToken(LITERAL_FLOAT)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state58(c: Char) {
        when {
            c == '+' || c == '-' -> goto(60)
            isHex(c) -> goto(59)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state59(c: Char) {
        when {
            isHex(c) -> goto(59)
            isSeparator(c) || isWhitespace(c) || isOperator(c) -> stepBackAndAddToken(LITERAL_FLOAT)
        }
    }

    private fun state60(c: Char) {
        when {
            isHex(c) -> goto(59)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state61(c: Char) {
        when {
            c == '.' -> goto(68)
            isDecimal(c) -> goto(62)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state62(c: Char) {
        when {
            isDecimal(c) -> goto(62)
            c == 'e' || c == 'E' -> goto(63)
            isSeparator(c) || isWhitespace(c) || isOperator(c) -> stepBackAndAddToken(LITERAL_FLOAT)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state63(c: Char) {
        when {
            isDecimal(c) -> goto(64)
            c == '+' || c == '-' -> goto(65)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state64(c: Char)  {
        when {
            isDecimal(c) -> goto(64)
            isSeparator(c) || isWhitespace(c) || isOperator(c) -> stepBackAndAddToken(LITERAL_FLOAT)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state65(c: Char) {
        when {
            isDecimal(c) -> goto(64)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state66(c: Char) {
        when {
            isBinary(c) -> goto(66)
            isSeparator(c) || isWhitespace(c) || isOperator(c) -> stepBackAndAddToken(LITERAL_FLOAT)
            else -> stepBackAndAddToken(ERROR)
        }
    }

    private fun state67(c: Char) {
        when {
            isValidChar(c) -> goto(67)
            else -> {
                when {
                    isBoolean(buffer.substring(0, buffer.length - 1)) -> stepBackAndAddToken(LITERAL_BOOLEAN)
                    isNil(buffer.substring(0, buffer.length - 1)) -> stepBackAndAddToken(LITERAL_NULL)
                    isPrimitive(buffer.substring(0, buffer.length - 1))
                            || isKeyword(buffer.substring(0, buffer.length - 1)) -> stepBackAndAddToken(KEYWORD)
                    else -> stepBackAndAddToken(IDENTIFIER)
                }
            }
        }
    }

    private fun state68(c: Char) {
        when {
            c == '.' || c == '<' -> {
                tokens.add(Token(buffer.substring(0, buffer.length - 3), LITERAL_INT))
                tokens.add(Token(buffer.substring(buffer.length - 3), OPERATOR))
                buffer.clear()
                state = 0
            }
            else -> stepBackAndAddToken(ERROR)
        }
    }
}