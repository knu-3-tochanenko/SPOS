fun main() {
    val lexer = Lexer("bugs.txt")
    lexer.run()

    val wrapper = LexerWrapper(lexer.tokens)

    wrapper.printToConsoleWrapped()
//    wrapper.printTokens()
//    wrapper.printSorted()
}