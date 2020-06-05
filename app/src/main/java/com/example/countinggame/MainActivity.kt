package com.example.countinggame


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import kotlin.random.Random.Default as Default

class MainActivity : AppCompatActivity() {

    private lateinit var op1:Button
    private lateinit var op2:Button
    private lateinit var op3:Button
    private lateinit var op4:Button
    private lateinit var mpYes: MediaPlayer
    private lateinit var mpNoo: MediaPlayer

    private lateinit var img:ImageView
    private var rightOption = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        op1 = findViewById(R.id.option_1)
        op2 = findViewById(R.id.option_2)
        op3 = findViewById(R.id.option_3)
        op4 = findViewById(R.id.option_4)

        img = findViewById(R.id.imageView)


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

        setGame()

        mpYes = MediaPlayer.create(this,R.raw.yes)
        mpNoo = MediaPlayer.create(this,R.raw.no)
    }

    private fun checkAnswer(option:Int){
        if (option == rightOption) {
            print("                GOOD!!  \nYou Selected Right option")
            mpYes.start()
            val handler = Handler()
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
        Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT).show()
    }

    private fun setGame(){
        val variant = Default.nextInt(1,3)
        var op1Num = 0
        var op2Num = 0
        var op3Num = 0
        val answer = findAnswer()
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
    }
}