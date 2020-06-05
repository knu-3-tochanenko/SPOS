data class Token(
    val string: String,
    val type: Type
) {

    enum class Type {
        ERROR,
        KEYWORD,
        COMMENT,
        OPERATOR,
        SEPARATOR,
        PRIMITIVE,
        DIRECTIVE,
        WHITESPACE,
        IDENTIFIER,
        LITERAL_INT,
        LITERAL_CHAR,
        LITERAL_FLOAT,
        LITERAL_STRING,
        LITERAL_BOOLEAN,
        LITERAL_NULL;
    }

    override fun toString() = "${type.name}=$string;"
}