package com.example.openheart.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.openheart.R

class ViewLocation : AppCompatActivity() {
    private lateinit var addData: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_location)

//        addData = findViewById(R.id.button2)
//
//        addData.setOnClickListener {
//            val intent1 = Intent(this, AddNewLocation::class.java)
//            startActivity(intent1)
//        }
    }
}