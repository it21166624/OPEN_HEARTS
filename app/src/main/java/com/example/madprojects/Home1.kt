package com.example.madprojects

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.madproject.R
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase

class Home1 : AppCompatActivity() {

    private lateinit var etOrganizationName: EditText
    private lateinit var etOrganizationID: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home1)

        etOrganizationName = findViewById(R.id.editTextText2)
        etOrganizationID = findViewById(R.id.editTextText3)
        etEmail = findViewById(R.id.editTextTextEmailAddress)
        etPassword = findViewById(R.id.editTextTextPassword2)

        dbRef = FirebaseDatabase.getInstance().getReference("User")

        val button4 = findViewById<Button>(R.id.button4)
        button4.setOnClickListener{

            saveUserData()
        }


    }
    private fun saveUserData() {

        //getting values
        val organizationName = etOrganizationName.text.toString()
        val organizationID = etOrganizationID.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if (organizationName.isEmpty()) {
            etOrganizationName.error = "Please enter organization Name"

        }
        if (organizationID.isEmpty()) {
            etOrganizationID.error = "Please enter organization ID"

        }
        if (email.isEmpty()) {
            etEmail.error = "Please enter email"

        }
        if (password.isEmpty()) {
            etPassword.error = "Please enter password"

        }

        val etOrganizationId = dbRef.push().key!!

        val user = UserModel(email,password)

        dbRef.child(etOrganizationId).setValue(user)
            .addOnCompleteListener{
                Toast.makeText(this,"User Signup Successfully", Toast.LENGTH_LONG).show()

                etOrganizationName.text.clear()
                etOrganizationID.text.clear()
                etEmail.text.clear()
                etPassword.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}