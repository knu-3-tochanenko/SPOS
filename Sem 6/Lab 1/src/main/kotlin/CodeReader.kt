import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.IllegalArgumentException

class CodeReader(fileName: String) {

    private var reader: BufferedReader
    private var line: String? = ""
    private var current = 0

    init {
        reader = BufferedReader(
            InputStreamReader(
                FileInputStream("src/main/resources/$fileName"),
                "UTF-8"
            )
        )
    }

    fun next(): Char {
        if (line!!.isEmpty() || line!!.length + 1 == current) {
            line = reader.readLine()

            if (line == null) {
                reader.close()
                return 0.toChar()
            }

            current = 0
        }
        if (line!!.length == current) {
            current++
            return '\n'
        }
        return line!![current++]
    }

    fun stepBack() {
//        if (current == 0)
//            throw IllegalArgumentException("You can't step back from the start of the line")
        if (current == 0) {
            return
        }
        current--
    }

    fun current() = line!![current]
}