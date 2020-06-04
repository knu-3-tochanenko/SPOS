fun main() {
    val reader = CodeReader("text.txt")

    var char = reader.next()

    while (char != 0.toChar()) {
        print(char)
        char = reader.next()
    }

    println(Token("hello", Token.Type.COMMENT))
}