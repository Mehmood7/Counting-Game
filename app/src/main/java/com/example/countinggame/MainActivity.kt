package com.example.countinggame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import kotlin.random.Random.Default as Default

class MainActivity : AppCompatActivity() {

    private lateinit var op1:Button
    private lateinit var op2:Button
    private lateinit var op3:Button
    private lateinit var op4:Button
    private lateinit var img:ImageView
    private lateinit var questionTV:TextView
    private lateinit var scoreTV:TextView

    private lateinit var mpYes: MediaPlayer
    private lateinit var mpNoo: MediaPlayer

    private var rightOption = 0
    private var lastAns = 0
    private var score = 0
    private var lives = 0
    private var names1 = arrayOf("Apples", "Cherries", "Fish", "Peaches", "Berries", "Bees",
        "Cats", "Apples", "Cars", "Hands", "Chicks", "Ducks")
    private var names2 = arrayOf("Bananas", "Strawberries", "Pine-Apples", "Pizza-Slices", "Cake-Pieces", "Water-Melons",
        "Unicorns", "Bananas", "Lemonades", "Oranges", "Soldiers", "Horses")

    private lateinit var myToast:Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myToast = Toast.makeText(applicationContext,"",Toast.LENGTH_SHORT)

        op1 = findViewById(R.id.option_1)
        op2 = findViewById(R.id.option_2)
        op3 = findViewById(R.id.option_3)
        op4 = findViewById(R.id.option_4)

        img = findViewById(R.id.imageView)

        questionTV = findViewById(R.id.question_text)
        scoreTV = findViewById(R.id.score_text)

        scoreTV.text = "Score: 0"
        questionTV.text = "\tLets count them."

        op1.setOnClickListener {
            checkAnswer(1)
        }
        op2.setOnClickListener {
            checkAnswer(2)
        }
        op3.setOnClickListener {
            checkAnswer(3)
        }
        op4.setOnClickListener {
            checkAnswer(4)
        }

        lives = 3
        setGame()

        mpYes = MediaPlayer.create(this,R.raw.yes)
        mpNoo = MediaPlayer.create(this,R.raw.no)
    }

    private fun checkAnswer(option:Int){
        if (rightOption == 0) return
        if (option == rightOption) {
            print("                GOOD!!  \nYou Selected Right option")
            mpYes.start()
            score += 3
            scoreTV.text = "Score: $score"
            val handler = Handler()
            rightOption = 0
            handler.postDelayed(
                {
                    setGame()
                }
                ,
                1750
            )
        }
        else{
            mpNoo.start()
            lives--
            if(lives == 0){
                val sharedPref = this?.getPreferences(Context.MODE_PRIVATE) ?: return
                with (sharedPref.edit()) {
                    putInt("Highest_score", score)
                    commit()
                }

                finish()
            }
            print("   OOPS!! \n Try again")

        }

    }

    private fun findAnswer(): Int
    {
        return Default.nextInt(1,13);
    }

    // Custom function to read images from assets dir

    private fun getBitmapFromAssets(fileName: String): Bitmap? {
        return try {
            BitmapFactory.decodeStream(assets.open(fileName))
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun print(msg: String){
        myToast.setText(msg)
        myToast.show()
    }

    private fun setGame(){
        val variant = Default.nextInt(1,3)
        var op1Num = 0
        var op2Num = 0
        var op3Num = 0
        var answer = findAnswer()

        // Prevent repetition of question
        while (answer == lastAns)
            answer = findAnswer()
        lastAns = answer
        rightOption = Default.nextInt(1,5)

        when(rightOption){
            1 -> op1.text = "$answer"
            2 -> op2.text = "$answer"
            3 -> op3.text = "$answer"
            4 -> op4.text = "$answer"
        }
        var wrongAns = findAnswer()
        while (wrongAns == answer) wrongAns = findAnswer()
        if(rightOption != 1) {
            op1.text = "$wrongAns"
            op1Num = wrongAns
        }

        wrongAns = findAnswer()
        while (wrongAns == answer || op1Num == wrongAns) wrongAns = findAnswer()
        if(rightOption != 2) {
            op2.text = "$wrongAns"
            op2Num = wrongAns
        }

        wrongAns = findAnswer()
        while (wrongAns == answer || op1Num == wrongAns || op2Num == wrongAns) wrongAns = findAnswer()
        if(rightOption != 3){
            op3.text = "$wrongAns"
            op3Num = wrongAns
        }

        wrongAns = findAnswer()
        while (wrongAns == answer  || op1Num == wrongAns || op2Num == wrongAns || op3Num == wrongAns) wrongAns = findAnswer()
        if(rightOption != 4) op4.text = "$wrongAns"


        val bitmap:Bitmap? = getBitmapFromAssets("pics/$answer-$variant.jpg")
        img.setImageBitmap(bitmap)

        val objectName = if(variant == 1 ) names1[answer-1] else names2[answer-1]

        questionTV.text = when(Default.nextInt(1,5)){
            1 -> "Can you count the $objectName?"
            2 -> "Lets count the $objectName."
            3 -> "Count the $objectName in this picture."
            4 -> "How many $objectName are in this picture."
            else -> "Lets count them."
        }

    }
}