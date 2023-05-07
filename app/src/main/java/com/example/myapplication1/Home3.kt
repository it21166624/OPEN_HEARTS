package com.example.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase

class Home3 : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home4)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        dbRef = FirebaseDatabase.getInstance().getReference("User")

        btnSignup.setOnClickListner{
            saveEmployeeData()
        }


    }

    private fun saveUserData(){

        //getting values
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if(email.isEmpty()){
            etEmail.error = "Please enter email"

        }
        if(password.isEmpty()){
            etPassword.error = "Please enter password"

        }

        etEmail.text.clear()
        etPassword.text.clear()
    }
}