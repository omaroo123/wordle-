import java.io.File
import kotlin.random.Random

fun isValid(word: String): Boolean {
    return Regex("^[A-Za-z]{5}$").matches(word)
}

fun readWordList(filename: String): MutableList<String> {
    val lines = File(filename).readLines()
    return lines.map { it.trim() }
        .filter { isValid(it) }
        .map { it.lowercase() }
        .toMutableList()
}

fun pickRandomWord(words: MutableList<String>): String {
    require(words.isNotEmpty()) { "Word list is empty." }
    val idx = Random.nextInt(words.size)
    return words.removeAt(idx)
}

fun obtainGuess(attempt: Int): String {
    while (true) {
        print("Attempt $attempt: ")
        val input = readLine()?.trim().orEmpty()
        if (isValid(input)) return input.lowercase()
        println("Invalid word. Please enter a 5-letter word consisting of letters A–Z.")
    }
}

fun evaluateGuess(guess: String, target: String): List<Int> {
    return (0 until 5).map { i ->
        if (guess[i].lowercaseChar() == target[i].lowercaseChar()) 1 else 0
    }
}

fun displayGuess(guess: String, matches: List<Int>) {
    val out = buildString {
        for (i in 0 until 5) {
            append(if (matches[i] == 1) guess[i].lowercaseChar() else '?')
        }
    }
    println(out)
}

