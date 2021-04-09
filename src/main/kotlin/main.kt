import java.io.File

/*
    Немного мотвации: хорошего regexp-а не придумать, будем действовать жадно.
    Первая встречная кавычка - открывающая. Затем ищем первую встречную, равную открывающей,
    перед которой не стоит нечётное число backslash-ей ('\'). Подстрока между ними (включая их),
    будет являтся строковым литералом. Если я не ошибаюсь, исключений быть не должно.
 */

fun findStringLiterals(string: String): List<String> {
    val literals = mutableListOf<String>()

    val n = string.length

    var openQuote = ' '
    var curStart = -1
    var bsCnt = 0

    fun handleQuote(quote: Char, i: Int) {
        if (openQuote == ' ') {
            openQuote = quote
            curStart = i
        } else if (openQuote == quote && bsCnt % 2 == 0) {
            literals.add(string.subSequence(curStart + 1, i).toString())
            openQuote = ' '
            curStart = -1
        }
    }

    for (i in 0 until n) {
        if (string[i] == '"' || string[i] == '\'') {
            handleQuote(string[i], i)
        }

        if (string[i] == '\\') {
            bsCnt += 1
        } else {
            bsCnt = 0
        }
    }

    return literals
}

fun getLitToLines(path: String): Map<String, List<Int>> {
    val litToLines = mutableMapOf<String, MutableList<Int>>()

    var lineNumber = -1
    File(path).forEachLine { line ->
        lineNumber += 1
        findStringLiterals(line).forEach {
            litToLines.getOrPut(it, { mutableListOf() }).add(lineNumber)
        }
    }

    val litToLinesFiltered = mutableMapOf<String, List<Int>>()
    for ((lit, lines) in litToLines.filter { it.value.size > 1 }){
        litToLinesFiltered[lit] = lines.toSortedSet().toList()
    }

    return litToLinesFiltered.toSortedMap()
}

fun main(args: Array<String>) {
    val path = args[0]

    getLitToLines(path).forEach { (lit, lines) ->
        println("Lines with '$lit': ${lines.joinToString(", ")}")
    }
}

