fun main() {
    val lexer = Lexer("text.txt")
    lexer.run()

    val wrapper = LexerWrapper(lexer.tokens)

    wrapper.printToConsoleWrapped()
    wrapper.printToConsole()

    wrapper.generateHtml()

    wrapper.printTokens()
    wrapper.printSorted()
}