package com.melissa.wordle

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.color
import nl.dionsegijn.konfetti.xml.KonfettiView

class MainActivity : AppCompatActivity() {
    private var numberOfGuesses: Int = 3
    private var wordToGuess: String = FourLetterWordList.getRandomFourLetterWord()
    private lateinit var viewKonfetti: KonfettiView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get ids for later use
        val correctWord: TextView = findViewById(R.id.correctWord)
        val currentGuess: EditText = findViewById(R.id.guessField)
        val guessBtn: Button = findViewById(R.id.guessButton)
        val resetBtn: Button = findViewById(R.id.resetButton)
        val guess1Label: TextView = findViewById(R.id.firstGuessLabel)
        val guess1: TextView = findViewById(R.id.firstGuess)
        val guess1CheckLabel: TextView = findViewById(R.id.firstGuessCheckLabel)
        val guess1Check: TextView = findViewById(R.id.firstGuessCheck)
        val guess2Label: TextView = findViewById(R.id.secondGuessLabel)
        val guess2: TextView = findViewById(R.id.secondGuess)
        val guess2CheckLabel: TextView = findViewById(R.id.secondGuessCheckLabel)
        val guess2Check: TextView = findViewById(R.id.secondGuessCheck)
        val guess3Label: TextView = findViewById(R.id.thirdGuessLabel)
        val guess3: TextView = findViewById(R.id.thirdGuess)
        val guess3CheckLabel: TextView = findViewById(R.id.thirdGuessCheckLabel)
        val guess3Check: TextView = findViewById(R.id.thirdGuessCheck)
        val streak: TextView = findViewById(R.id.streakCounter)
        viewKonfetti = findViewById(R.id.konfettiView)

        // Get the correct word TV and set it to selected word
        correctWord.text = wordToGuess
        Log.v("correct", wordToGuess)
        // Wait for user to guess with guess button click listener
        guessBtn.setOnClickListener {
            val userGuessEntered = currentGuess.text.toString()
            // Check if we have enough guesses to process
            if (numberOfGuesses > 0) {
                if (userGuessEntered.length != 4 || !isOnlyLetters(userGuessEntered)) {
                    Toast.makeText(
                        it.context,
                        "Incorrect input. 4 letters and [Aa-Zz] only.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (numberOfGuesses == 3) {
                    guess1.text = guess1.text.toString().plus(userGuessEntered).uppercase()
                    guess1Check.text = checkGuess(userGuessEntered)
                    guess1Label.visibility = VISIBLE
                    guess1.visibility = VISIBLE
                    guess1CheckLabel.visibility = VISIBLE
                    guess1Check.visibility = VISIBLE

                } else if (numberOfGuesses == 2) {
                    guess2.text = guess2.text.toString().plus(userGuessEntered).uppercase()
                    guess2Check.text = checkGuess(userGuessEntered)

                    guess2Label.visibility = VISIBLE
                    guess2.visibility = VISIBLE
                    guess2CheckLabel.visibility = VISIBLE
                    guess2Check.visibility = VISIBLE
                } else {
                    guess3.text = guess3.text.toString().plus(userGuessEntered).uppercase()
                    guess3Check.text = checkGuess(userGuessEntered)
                    guess3Label.visibility = VISIBLE
                    guess3.visibility = VISIBLE
                    guess3CheckLabel.visibility = VISIBLE
                    guess3Check.visibility = VISIBLE

                    // Last guess used up, show correct word
                    correctWord.visibility = VISIBLE
                    guessBtn.visibility = INVISIBLE
                    resetBtn.visibility = VISIBLE
                    Toast.makeText(it.context, "Out of guesses!", Toast.LENGTH_SHORT).show()
                    if (userGuessEntered.uppercase() == wordToGuess.uppercase()) {
                        viewKonfetti.start(Presets.parade())
                        streak.text = streak.text.toString().toInt().plus(1).toString()
                    }
                }
            } else {
                Toast.makeText(
                    it.context,
                    "Guesses remaining: $numberOfGuesses",
                    Toast.LENGTH_SHORT
                ).show()
            }
            numberOfGuesses--
            currentGuess.setText("")
            currentGuess.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }
        resetBtn.setOnClickListener {
            wordToGuess = FourLetterWordList.getRandomFourLetterWord()
            numberOfGuesses = 3
            correctWord.visibility = INVISIBLE
            correctWord.text = wordToGuess
            Log.v("correct", wordToGuess)

            guess1Label.visibility = INVISIBLE
            guess1.visibility = INVISIBLE
            guess1.text = ""

            guess2Label.visibility = INVISIBLE
            guess2.visibility = INVISIBLE
            guess2.text = ""

            guess3Label.visibility = INVISIBLE
            guess3.visibility = INVISIBLE
            guess3.text = ""

            guess1CheckLabel.visibility = INVISIBLE
            guess1Check.visibility = INVISIBLE
            guess1Check.text = ""

            guess2CheckLabel.visibility = INVISIBLE
            guess2Check.visibility = INVISIBLE
            guess2Check.text = ""

            guess3CheckLabel.visibility = INVISIBLE
            guess3Check.visibility = INVISIBLE
            guess3Check.text = ""

            guessBtn.visibility = VISIBLE
            resetBtn.visibility = INVISIBLE
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a SpannableStringBuilder of the original guess, where:
     *   green font represents the right letter in the right place
     *   red font represents the right letter in the wrong place
     *   gray font represents a letter not in the target word
     */
    private fun checkGuess(guess: String): SpannableStringBuilder {
        val sBuilder = SpannableStringBuilder()
        for (i in 0..3) {
            var guessUpper = guess[i].uppercaseChar()
            if (guessUpper == wordToGuess[i]) {
                sBuilder.color(getColor((R.color.green_500))) { append(guessUpper) }
            } else if (guessUpper in wordToGuess) {
                sBuilder.color(getColor((R.color.red_500))) { append(guessUpper) }
            } else {
                sBuilder.append(guessUpper)
            }
        }
        return sBuilder
    }

    /**
     * Credit: https://stackoverflow.com/questions/61322129/check-if-a-string-contains-only-letters
     * Parameters / Fields:
     *   word : String - what the user entered as their guess
     *
     * Returns a Boolean, where:
     *   true, if all letters
     *   false, if other characters are detected
     */
    private fun isOnlyLetters(word: String): Boolean {
        val regex = "^[A-Za-z]*$".toRegex()
        return regex.matches(word)
    }
}
