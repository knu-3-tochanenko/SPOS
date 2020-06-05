fun main() {
    val lexer = Lexer("text.txt")
    lexer.run()

    val tokens = lexer.tokens

    for (token in tokens)
        println(token)
}