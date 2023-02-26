package com.melissa.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.melissa.wordle.FourLetterWordList

class MainActivity : AppCompatActivity() {
    private val NUMBER_OF_GUESSES = 3
    private var wordToGuess: String = FourLetterWordList.getRandomFourLetterWord()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val guessTextField = findViewById<EditText>(R.id.guessEditText)

        //Log.v("debug", wordToGuess)

        //val screenView = findViewById<TextView>(R.id.wordView)
        //screenView.text = FourLetterWordList.getRandomFourLetterWord()
        findViewById<Button>(R.id.guessEnterButton).setOnClickListener{
            val userGuessEntered = guessTextField.text.toString()
            Log.v("guess", userGuessEntered)
            guessTextField.setText("")

            if (NUMBER_OF_GUESSES > 0) {
                checkGuess(userGuessEntered)
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
