import java.util.regex.Pattern

fun isRegular(c: Char) = isSeparator(c) || isValidChar(c) || isWhitespace(c)

fun isSeparator(c: Char) = c == '(' || c == ')' || c == '{'
        || c == '}' || c == '[' || c == ']'
        || c == ';' || c == ',' || c == ':' || c == '\n'

fun isValidChar(c: Char) = Character.isJavaIdentifierPart(c) || c == '$'

fun isWhitespace(c: Char) = Character.isWhitespace(c)

//fun isNewLine(c: Char) = Character.isWhitespace(c) && (c != ' ' || c != '\t')
fun isNewLine(c: Char) = c == '\n'

fun isLetter(c: Char) = Pattern.matches("\\w", c.toString())

fun isOperator(c: Char) = c == '=' || c == '>' || c == '<'
        || c == '!' || c == '~' || c == ':'
        || c == '?' || c == '&' || c == '|'
        || c == '+' || c == '-' || c == '*'
        || c == '/' || c == '^' || c == '%'
        || c == '`' || c == '#' || c == '@' || c == '.'

fun isSpecial(sequence: String) = "\\b" == sequence || "\\t" == sequence || "\\n" == sequence
        || "\\" == sequence || "'" == sequence || "\"" == sequence
        || "\\r" == sequence || "\\f" == sequence

fun isOctal(c: Char) = Pattern.matches("[0-7]", c.toString())

fun isBinary(c: Char) = c == '0' || c == '1'

fun isHex(c: Char) = Pattern.matches("\\d|[a-fA-F]", c.toString())

fun isDecimal(c: Char) = Pattern.matches("[0-9]", c.toString())

fun isNil(word: String) = word == "nil"

fun isBoolean(word: String) = word == "true" || word == "false"

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

fun isPrimitive(word: String) = primitives.contains(word)

private val primitives = listOf(
    "Int",
    "UInt",
    "Float",
    "Double",
    "Bool",
    "String",
    "Character",
    "Optional",
    "Tuples",
    "Int8",
    "UInt8",
    "Int32",
    "UInt32",
    "Int64",
    "UInt64"
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