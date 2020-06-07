package com.example.countinggame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Menu : AppCompatActivity() {

    private lateinit var playBtn:Button
    private lateinit var challengeBtn:Button
    private lateinit var hofBtn:Button
    private lateinit var settingBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        playBtn = findViewById(R.id.play_btn)
        playBtn.setOnClickListener {
            val playIntent = Intent(
                this,
                MainActivity::class.java
            )
            startActivity(playIntent)
        }

        hofBtn = findViewById(R.id.HOF_btn)
        hofBtn.setOnClickListener {
            val hofIntent = Intent(
                this,
                HallOfFame::class.java
            )
            startActivity(hofIntent)
        }


    }


}
