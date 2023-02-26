package com.melissa.wordle

import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var numberOfGuesses: Int = 3
    private var wordToGuess: String = FourLetterWordList.getRandomFourLetterWord()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get ids for later use
        val correctWord: TextView = findViewById(R.id.correctWord)
        val currentGuess: EditText = findViewById(R.id.guessField)
        val guessBtn: Button = findViewById(R.id.guessButton)
        val guess1: TextView = findViewById(R.id.firstGuess)
        val guess1Check: TextView = findViewById(R.id.firstGuessCheck)
        val guess2: TextView = findViewById(R.id.secondGuess)
        val guess2Check: TextView = findViewById(R.id.secondGuessCheck)
        val guess3: TextView = findViewById(R.id.thirdGuess)
        val guess3Check: TextView = findViewById(R.id.thirdGuessCheck)

        // Get the correct word TV and set it to selected word
        correctWord.text = wordToGuess

        // Wait for user to guess with guess button click listener
        guessBtn.setOnClickListener {
            val userGuessEntered = currentGuess.text.toString()
            // Check if we have enough guesses to process
            if (numberOfGuesses > 0) {
                if (numberOfGuesses == 3) {
                    guess1.text = guess1.text.toString().plus(userGuessEntered)
                    guess1Check.text =
                        guess1Check.text.toString().plus(checkGuess(userGuessEntered))
                    guess1.visibility = VISIBLE
                    guess1Check.visibility = VISIBLE
                } else if (numberOfGuesses == 2) {
                    guess2.text = guess2.text.toString().plus(userGuessEntered)
                    guess2Check.text =
                        guess2Check.text.toString().plus(checkGuess(userGuessEntered))
                    guess2.visibility = VISIBLE
                    guess2Check.visibility = VISIBLE
                } else {
                    guess3.text = guess3.text.toString().plus(userGuessEntered)
                    guess3Check.text =
                        guess3Check.text.toString().plus(checkGuess(userGuessEntered))
                    guess3.visibility = VISIBLE
                    guess3Check.visibility = VISIBLE
                    // Last guess used up, show correct word
                    correctWord.visibility = VISIBLE
                    guessBtn.isEnabled = false
                    Toast.makeText(it.context, "Out of guesses!", Toast.LENGTH_SHORT).show()
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
    }


    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String): String {
        Log.v("checkGuess", guess)
        Log.v("checkGuess", wordToGuess)
        var result = ""
        for (i in 0..3) {
            if (guess[i].uppercaseChar() == wordToGuess[i]) {
                result += "O"
            } else if (guess[i].uppercaseChar() in wordToGuess) {
                result += "+"
            } else {
                result += "X"
            }
        }
        return result
    }
}
