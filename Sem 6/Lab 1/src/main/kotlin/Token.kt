class Token(
    private val string: String,
    private val type: Type
) {

    enum class Type {
        ERROR,
        KEYWORD,
        COMMENT,
        OPERATOR,
        SEPARATOR,
        WHITESPACE,
        IDENTIFIER,
        LITERAL_INT,
        LITERAL_CHAR,
        LITERAL_FLOAT,
        LITERAL_STRING,
        LITERAL_BOOLEAN,
        LITERAL_NULL;
    }

    override fun toString() = "${type} : {$string}"
}