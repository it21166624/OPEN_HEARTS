package com.example.madprojects

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.madproject.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnContinue = findViewById(R.id.continuebtn)
        btnContinue.setOnClickListener{
            val intent = Intent(this,Home2::class.java)
            startActivity(intent)
        }
    }
}