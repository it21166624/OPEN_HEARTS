package com.example.amaya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class ProjectDashboard : AppCompatActivity() {

    private lateinit var imageButton7 : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_dashboard)

        imageButton7 = findViewById(R.id.imageButton7)

        imageButton7.setOnClickListener {
            val intent = Intent(this,ProjectView::class.java)
            startActivity(intent)
        }
    }
}