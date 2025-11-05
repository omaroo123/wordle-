import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe

class WordleTest : StringSpec({

    "isValid accepts exactly 5 letters" {
        isValid("crane") shouldBe true
        isValid("CRANE") shouldBe true
        isValid("boat1") shouldBe false
        isValid("abcd") shouldBe false
        isValid("toooolong") shouldBe false
        isValid("sp ce") shouldBe false
        isValid("crane!") shouldBe false
    }

    "readWordList filters to valid 5-letter words and lowercases them" {
        val tmp = createTempFile(prefix = "words", suffix = ".txt")
        tmp.writeText(
            """
            apple
            boat
            bad
            crane!
            STONE
            """.trimIndent()
        )

        val words = readWordList(tmp.absolutePath)
        words shouldHaveSize 3
        words shouldContain "apple"
        words shouldContain "boat"
        words shouldContain "stone"
        words shouldNotContain "bad"
        words shouldNotContain "crane!"
        tmp.deleteOnExit()
    }

    "pickRandomWord returns an element and removes exactly one from the list" {
        val words = mutableListOf("alpha", "bravo", "charlie", "delta", "echo")
        val beforeSize = words.size
        val picked = pickRandomWord(words)
        listOf("alpha", "bravo", "charlie", "delta", "echo") shouldContain picked
        words.size shouldBe (beforeSize - 1)
        words shouldNotContain picked
    }

    "evaluateGuess marks only exact positional matches with 1" {
        val guess = "apple"
        val target = "angle"
        evaluateGuess(guess, target) shouldBe listOf(1, 0, 0, 1, 1)
        evaluateGuess("stone", "stone") shouldBe listOf(1,1,1,1,1)
        evaluateGuess("crane", "pilot") shouldBe listOf(0,0,0,0,0)
    }
})
