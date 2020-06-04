import java.util.regex.Pattern

fun isSeparator(c: Char) = c == '(' || c == ')' || c == '{'
        || c == '}' || c == '[' || c == ']'
        || c == ';' || c == ',' || c == '@'

fun isOperator(c: Char) = c == '=' || c == '>' || c == '<'
        || c == '!' || c == '~' || c == ':'
        || c == '?' || c == '&' || c == '|'
        || c == '+' || c == '-' || c == '*'
        || c == '/' || c == '^' || c == '%'

fun isSpecial(sequence: String) = "\\b" == sequence || "\\t" == sequence || "\\n" == sequence
        || "\\" == sequence || "'" == sequence || "\"" == sequence
        || "\\r" == sequence || "\\f" == sequence

fun isOctal(c: Char) = Pattern.matches("[0-7]", c.toString())

fun isBinary(c: Char) = c == '0' || c == '1'

fun isHex(c: Char) = Pattern.matches("\\d|[a-fA-F]", c.toString())

fun isFloat(c: Char) = c == 'f' || c == 'F' || c == 'd' || c == 'D'

private val keywords = listOf(
    "associatedtype",
    "class",
    "deinit",
    "enum",
    "extension",
    "fileprivate",
    "func",
    "import",
    "init",
    "inout",
    "internal",
    "let",
    "open",
    "operator",
    "private",
    "protocol",
    "public",
    "rethrows",
    "static",
    "struct",
    "subscript",
    "typealias",
    "var",
    "break",
    "case",
    "continue",
    "default",
    "defer",
    "do",
    "else",
    "fallthrough",
    "for",
    "guard",
    "if",
    "in",
    "repeat",
    "return",
    "switch",
    "where",
    "while",
    "as",
    "Any",
    "catch",
    "false",
    "is",
    "nil",
    "super",
    "self",
    "Self",
    "throw",
    "throws",
    "true",
    "try"
)

fun isKeyword(word: String) = keywords.contains(word)

private val directives = listOf(
    "available",
    "colorLiteral",
    "column",
    "else",
    "elseif",
    "endif",
    "error",
    "file",
    "fileLiteral",
    "function",
    "if",
    "imageLiteral",
    "line",
    "selector",
    "sourceLocation",
    "warning"
)

fun isDirective(word: String) = directives.contains(word)