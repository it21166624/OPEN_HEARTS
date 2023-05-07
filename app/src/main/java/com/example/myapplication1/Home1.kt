package com.example.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Organization
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase

class Home1 : AppCompatActivity() {

    private lateinit var etOrganizationName: EditText
    private lateinit var etOrganizationID: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home4)

        etOrganizationName = findViewById(R.id.etOrganizationName)
        etOrganizationID = findViewById(R.id.organizationID)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        dbRef = FirebaseDatabase.getInstance().getReference("User")

        btnSignup.setOnClickListner {
            saveEmployeeData()
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

        etOrganizationName.text.clear()
        etOrganizationID.text.clear()
        etEmail.text.clear()
        etPassword.text.clear()

    }

}