package com.example.firebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication1.AdminHome

class MainActivity : AppCompatActivity()

      private lateinit var btnUpdate: button
      private lateinit var btnDelete: button


      override fun onCreate(savedInstanceState: Bundle?){
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)

          btnUpdate = findViewById(R.id.btnUpdate)
          btnDelete = findViewbyId(R.id.btnDelete)

          btnUpdate.setOnClickListener{
              var intent = Intent(this,AdminHome::class.java)
              startActivity
          }
      }