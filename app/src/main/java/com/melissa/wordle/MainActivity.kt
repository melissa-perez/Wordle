package com.melissa.wordle

import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var numberOfGuesses: Int = 3
    private var wordToGuess: String = FourLetterWordList.getRandomFourLetterWord()
    private val correctWordTextView: TextView = findViewById(R.id.correctWordTextView)
    private val currentGuess: EditText = findViewById(R.id.guessEditText)
    private val guessBtn: Button = findViewById(R.id.guessEnterButton)
    private val guess1 : TextView = findViewById(R.id.guessOneText)
    private val guess1Check: TextView = findViewById(R.id.guessOneCheckText)
    private val guess2: TextView = findViewById(R.id.guessTwoText)
    private val guess2Check: TextView = findViewById(R.id.guessTwoCheckText)
    private val guess3: TextView = findViewById(R.id.guessThreeText)
    private val guess3Check: TextView = findViewById(R.id.guessThreeCheckText)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. get the correct word TV and set it to selected word
        correctWordTextView.text = wordToGuess
        Log.v("word", wordToGuess)

        // 2. wait for user to guess with guess button click listener
        guessBtn.setOnClickListener{
            val userGuessEntered = currentGuess.text.toString()
            Log.v("guess", userGuessEntered)
            currentGuess.setText("")

            // 1. Check if we have enough guesses to process first
            if (numberOfGuesses > 0) {
                numberOfGuesses--
                Toast.makeText(it.context, "Guesses remaining: $numberOfGuesses", Toast.LENGTH_SHORT).show()
                if (numberOfGuesses == 2) {
                    guess1.text = guess1.text.toString().plus(userGuessEntered)
                    guess1Check.text = guess1Check.text.toString().plus(checkGuess(userGuessEntered))
                }
                else if (numberOfGuesses == 1) {
                    guess2.text = guess2.text.toString().plus(userGuessEntered)
                    guess2Check.text = guess2Check.text.toString().plus(checkGuess(userGuessEntered))
                }
                else {
                    guess3.text = guess3.text.toString().plus(userGuessEntered)
                    guess3Check.text = guess3Check.text.toString().plus(checkGuess(userGuessEntered))
                }
            }
            else {
                correctWordTextView.visibility = VISIBLE
                guessBtn.isEnabled = false
                Toast.makeText(it.context, "Out of guesses!", Toast.LENGTH_SHORT).show()
            }
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
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}
