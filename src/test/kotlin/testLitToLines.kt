import kotlin.test.Test
import kotlin.test.assertTrue

class TestLitToLines {
    private fun litToLinesIsCorrect(correct: Map<String, List<Int>>, actual: Map<String, List<Int>>): Boolean {
        if (!(correct.keys.toTypedArray() contentEquals actual.keys.toTypedArray())) {
            return false
        }

        for ((lit, correctLines) in correct) {
            val actualLines = actual[lit] ?: return false
            if (!(correctLines.toTypedArray() contentEquals actualLines.toTypedArray())) {
                return false
            }
        }

        return true
    }

    @Test
    fun testExample() {
        val correct = mapOf(
            "id" to listOf(0, 5),
            "value" to listOf(0, 6),
        ).toSortedMap()
        assertTrue { litToLinesIsCorrect(correct, getLitToLines("python_scripts/example.py")) }
    }

    @Test
    fun testBigScript() {
        val correct = mapOf(
            "id" to listOf(1, 6),
            "\"ReallyBigDataSet\"" to listOf(0, 9),
        ).toSortedMap()
        assertTrue { litToLinesIsCorrect(correct, getLitToLines("python_scripts/big_script.py")) }
    }
}