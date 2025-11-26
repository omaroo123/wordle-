package org.example

fun main() {
    val words = try {
        readWordList("data/words.txt")
    } catch (e: Exception) {
        println("Error: Could not read word list from data/words.txt (${e.message}).")
        return
    }

    if (words.isEmpty()) {
        println("Error: Word list is empty after filtering invalid entries.")
        return
    }

    val target = pickRandomWord(words)
    val maxAttempts = 10

    for (attempt in 1..maxAttempts) {
        val guess = obtainGuess(attempt)
        val matches = evaluateGuess(guess, target)
        displayGuess(guess, matches)

        if (matches.all { it == 1 }) {
            println("Correct! You guessed the word \"$target\" in $attempt attempt(s).")
            return
        }
    }

    println("Out of attempts! The word was \"$target\".")
}

