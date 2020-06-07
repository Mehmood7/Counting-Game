package com.example.countinggame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class HallOfFame : AppCompatActivity() {

    private lateinit var highestScore:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hall_of_fame)

        highestScore = findViewById(R.id.highest_score)

        val sharedPref = getSharedPreferences("prefs",Context.MODE_PRIVATE) ?: return
        val highScore = sharedPref.getInt("Highest_score", 0)

        highestScore.text = "Highest Score: $highScore."
    }
}
