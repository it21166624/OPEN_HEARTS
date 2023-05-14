package com.example.openheart.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.openheart.R

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var addData: Button
    private lateinit var fetchData: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference("location")

        addData = findViewById(R.id.button10)
        fetchData = findViewById(R.id.button11)

        addData.setOnClickListener {
            val intent1 = Intent(this, AddNewLocation::class.java)
            startActivity(intent1)
        }

        fetchData.setOnClickListener {
            val intent2 = Intent(this, Fetching_Data::class.java)
            startActivity(intent2)


        }
    }
}