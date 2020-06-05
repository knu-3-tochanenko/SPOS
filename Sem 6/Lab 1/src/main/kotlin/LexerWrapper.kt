class LexerWrapper(
    private val tokens: MutableList<Token>
) {
    fun printToConsole() {
        for (token in tokens) {
            when (token.type) {
                Token.Type.ERROR -> print("[${token.string.red()}]")
                Token.Type.COMMENT -> print(token.string.green())
                Token.Type.LITERAL_CHAR,
                Token.Type.LITERAL_BOOLEAN,
                Token.Type.LITERAL_FLOAT,
                Token.Type.LITERAL_INT,
                Token.Type.LITERAL_NULL,
                Token.Type.LITERAL_STRING -> print(token.string.cyan())
                Token.Type.KEYWORD, Token.Type.PRIMITIVE -> print(token.string.purple())
                Token.Type.OPERATOR -> print("[${token.string.blue()}]")
                Token.Type.SEPARATOR -> print(token.string.white())
                else -> print(token.string)
            }

        }
    }

    fun printTokens() {
        for (token in tokens)
            println(token)
    }

    fun printSorted() {

    }

}