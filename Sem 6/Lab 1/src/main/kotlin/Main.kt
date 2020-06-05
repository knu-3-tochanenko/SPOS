fun main() {
    val lexer = Lexer("text.txt")
    lexer.run()

    val wrapper = LexerWrapper(lexer.tokens)

    wrapper.printToConsole()
    wrapper.printTokens()
}