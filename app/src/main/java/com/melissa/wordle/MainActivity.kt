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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 1. get ids for later use
        val correctWordTextView: TextView = findViewById(R.id.correctWordTextView)
        val currentGuess: EditText = findViewById(R.id.guessEditText)
        val guessBtn: Button = findViewById(R.id.guessEnterButton)
        val guess1 : TextView = findViewById(R.id.guessOne)
        //val guess1Check: TextView = findViewById(R.id.guessOneCheck)
        //val guess2: TextView = findViewById(R.id.guessTwo)
        //val guess2Check: TextView = findViewById(R.id.guessTwoCheck)
        //val guess3: TextView = findViewById(R.id.guessThree)
        //val guess3Check: TextView = findViewById(R.id.guessThreeCheckText)

        // 1. get the correct word TV and set it to selected word
        correctWordTextView.text = wordToGuess
        Log.v("word", wordToGuess)
        Log.v("word", guess1.toString())

        // 2. wait for user to guess with guess button click listener
        guessBtn.setOnClickListener{
            val userGuessEntered = currentGuess.text.toString()
            Log.v("guess", userGuessEntered)

            // 1. Check if we have enough guesses to process first
            //if (numberOfGuesses > 1) {
            Log.v("guess", numberOfGuesses.toString())

            Log.v("guess", "Hello from guess 3")
            Log.v("guess", guess1.text.toString())
            guess1.visibility = VISIBLE
               /* else if (numberOfGuesses == 2) {
                   // guess2.text = guess2.text.toString().plus(userGuessEntered)
                   // guess2Check.text = guess2Check.text.toString().plus(checkGuess(userGuessEntered))
                    //guess2.visibility = VISIBLE
                    //guess2Check.visibility = VISIBLE
                }
                else {
                   // guess3.text = guess3.text.toString().plus(userGuessEntered)
                    //guess3Check.text = guess3Check.text.toString().plus(checkGuess(userGuessEntered))
                    //guess3.visibility = VISIBLE
                    //guess3Check.visibility = VISIBLE
                }*/
            numberOfGuesses--
            Toast.makeText(it.context, "Guesses remaining: $numberOfGuesses", Toast.LENGTH_SHORT).show()
           // }
          //  else {
            correctWordTextView.visibility = VISIBLE
            guessBtn.isEnabled = false
            Toast.makeText(it.context, "Out of guesses!", Toast.LENGTH_SHORT).show()
           // }
            //guess1.visibility = VISIBLE
            currentGuess.setText("")
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
