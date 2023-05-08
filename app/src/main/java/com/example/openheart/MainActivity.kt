package com.example.openheart

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.openheart.R.id.button9
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var addData: Button
    private lateinit var fetchData: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        addData = findViewById(R.id.button10)
        fetchData = findViewById(R.id.button11)

        addData.setOnClickListener {
            val intent1 = Intent(this, AddNewLocation::class.java)
            startActivity(intent1)
        }

        fetchData.setOnClickListener {
            val intent2 = Intent(this, AddNewLocation::class.java)
            startActivity(intent2)


        }
    }
}