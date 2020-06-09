import java.io.File

class LexerWrapper(
    private val tokens: MutableList<Token>
) {
    fun printToConsole() {
        println("PRINT COLORED CODE IN CONSOLE ---------------------------------------------------".green())

        for (token in tokens) {
            when (token.type) {
                Token.Type.ERROR -> print(token.string.red())
                Token.Type.COMMENT -> print(token.string.green())
                Token.Type.LITERAL_CHAR,
                Token.Type.LITERAL_BOOLEAN,
                Token.Type.LITERAL_FLOAT,
                Token.Type.LITERAL_INT,
                Token.Type.LITERAL_NULL,
                Token.Type.LITERAL_STRING -> print(token.string.cyan())
                Token.Type.KEYWORD, Token.Type.PRIMITIVE, Token.Type.DIRECTIVE -> print(token.string.purple())
                Token.Type.OPERATOR -> print(token.string.blue())
                Token.Type.SEPARATOR -> print(token.string.white())
                else -> print(token.string)
            }

        }
    }

    fun printToConsoleWrapped() {
        println("PRINT COLORED CODE IN CONSOLE ---------------------------------------------------".green())

        for (token in tokens) {
            when (token.type) {
                Token.Type.ERROR -> print("[${token.string.red()}]")
                Token.Type.COMMENT -> print("[${token.string.green()}]")
                Token.Type.LITERAL_CHAR,
                Token.Type.LITERAL_BOOLEAN,
                Token.Type.LITERAL_FLOAT,
                Token.Type.LITERAL_INT,
                Token.Type.LITERAL_NULL,
                Token.Type.LITERAL_STRING -> print("[${token.string.cyan()}]")
                Token.Type.KEYWORD, Token.Type.PRIMITIVE, Token.Type.DIRECTIVE -> print("[${token.string.purple()}]")
                Token.Type.OPERATOR -> print("[${token.string.blue()}]")
                Token.Type.SEPARATOR -> print("[${token.string.white()}]")
                else -> print("[${token.string}]")
            }

        }
    }

    fun printTokens() {
        println("PRINT ALL TOKENS ----------------------------------------------------------------".green())
        for (token in tokens)
            if (token.type != Token.Type.WHITESPACE) println(token)
    }

    fun printSorted() {
        println("PRINT ALL TOKENS SORTED BY TYPE -------------------------------------------------".green())
        val sorted = tokens.toMutableList()
        sorted.sortBy { it.type }
        for (token in sorted)
            if (token.type != Token.Type.WHITESPACE) println(token)
    }

    private val header = """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Syntax Highlight</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>"""

    fun generateHtml() {
        val file = File("src/main/resources/index.html")
        val writer = file.printWriter()
            writer.print(header)
        for (token in tokens) {
            writer.print("<span class=\"${token.type.name.toLowerCase()}\">${token.string}</span>")
        }
        writer.print("""</body>
</html>""")
        writer.close()
    }

}