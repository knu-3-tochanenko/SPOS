import Token.Type.*
import java.io.File

class LexerWrapper(
    private val tokens: MutableList<Token>
) {
    fun printToConsole() {
        println("PRINT COLORED CODE IN CONSOLE ---------------------------------------------------".green())

        for (token in tokens) {
            when (token.type) {
                ERROR -> print(token.string.red())
                COMMENT -> print(token.string.green())
                LITERAL_CHAR,
                LITERAL_BOOLEAN,
                LITERAL_FLOAT,
                LITERAL_INT,
                LITERAL_NULL,
                LITERAL_STRING -> print(token.string.cyan())
                KEYWORD, PRIMITIVE, DIRECTIVE, INSTRUCTION -> print(token.string.purple())
                OPERATOR -> print(token.string.blue())
                SEPARATOR -> print(token.string.white())
                else -> print(token.string)
            }

        }
    }

    fun printToConsoleWrapped() {
        println("PRINT COLORED CODE IN CONSOLE ---------------------------------------------------".green())

        for (token in tokens) {
            when (token.type) {
                ERROR -> print("[${token.string.red()}]")
                COMMENT -> print("[${token.string.green()}]")
                LITERAL_CHAR,
                LITERAL_BOOLEAN,
                LITERAL_FLOAT,
                LITERAL_INT,
                LITERAL_NULL,
                LITERAL_STRING -> print("[${token.string.cyan()}]")
                KEYWORD, PRIMITIVE, DIRECTIVE, INSTRUCTION -> print("[${token.string.purple()}]")
                OPERATOR -> print("[${token.string.blue()}]")
                SEPARATOR -> print("[${token.string.white()}]")
                else -> print("[${token.string}]")
            }

        }
    }

    fun printTokens() {
        println("PRINT ALL TOKENS ----------------------------------------------------------------".green())
        for (token in tokens)
            if (token.type != WHITESPACE) println(token)
    }

    fun printSorted() {
        println("PRINT ALL TOKENS SORTED BY TYPE -------------------------------------------------".green())
        val sorted = tokens.toMutableList()
        sorted.sortBy { it.type }
        for (token in sorted)
            if (token.type != WHITESPACE) println(token)
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