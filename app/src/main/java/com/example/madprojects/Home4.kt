
package com.example.madprojects

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.madproject.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.System.err

class Home4 : AppCompatActivity() {


    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home3)

        etEmail = findViewById(R.id.editTextTextEmailAddress2)
        etPassword = findViewById(R.id.editTextTextPassword)
        var btnSignup = findViewById<Button>(R.id.button6)

        dbRef = FirebaseDatabase.getInstance().getReference("User")

        btnSignup.setOnClickListener() {
            saveUserData()

        }
    }

    private fun saveUserData() {

        //getting values
        var email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if (email.isEmpty()) {
            etEmail.error = "Please enter email"

        }
        if (password.isEmpty()) {
            etPassword.error = "Please enter password"

        }

       var  userID = dbRef.push().key!!

        val user = UserModel(userID,email, password)

        dbRef.child(userID).setValue(user)
            .addOnCompleteListener{
                Toast.makeText(this,"User Signup Successfully",Toast.LENGTH_LONG).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Error ${err}",Toast.LENGTH_LONG).show()

            }

        etEmail.text.clear()
        etPassword.text.clear()
    }
}





