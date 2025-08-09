package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "X"
    private var board = Array(3) { arrayOfNulls<String>(3) }
    private lateinit var buttons: Array<Array<Button>>
    private lateinit var resultTextView: TextView
    private lateinit var playAgainButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.titleText)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttons = arrayOf(
            arrayOf(findViewById(R.id.button00), findViewById(R.id.button01), findViewById(R.id.button02)),
            arrayOf(findViewById(R.id.button10), findViewById(R.id.button11), findViewById(R.id.button12)),
            arrayOf(findViewById(R.id.button20), findViewById(R.id.button21), findViewById(R.id.button22))
        )

        resultTextView = findViewById(R.id.resultTextView)
        playAgainButton = findViewById(R.id.playAgainButton)

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].setOnClickListener {
                    onButtonClick(i, j)
                }
            }
        }

        playAgainButton.setOnClickListener {
            resetGame()
        }

        resetGame()
    }

    private fun onButtonClick(row: Int, col: Int) {
        if (board[row][col] != null) return

        board[row][col] = currentPlayer
        buttons[row][col].text = currentPlayer
        buttons[row][col].isEnabled = false

        if (checkWin()) {
            showResult("$currentPlayer wins!")
        } else if (checkDraw()) {
            showResult("It's a draw!")
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
        }
    }

    private fun checkWin(): Boolean {
        for (i in 0..2) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)
            ) return true
        }

        if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
            (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
        ) return true

        return false
    }

    private fun checkDraw(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == null) return false
            }
        }
        return true
    }

    private fun showResult(message: String) {
        resultTextView.text = message
        resultTextView.visibility = View.VISIBLE
        playAgainButton.visibility = View.VISIBLE
        disableAllButtons()
    }

    private fun disableAllButtons() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = false
            }
        }
    }

    private fun resetGame() {
        currentPlayer = "X"
        resultTextView.visibility = View.GONE
        playAgainButton.visibility = View.GONE

        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = null
                buttons[i][j].text = ""
                buttons[i][j].isEnabled = true
            }
        }
    }
}