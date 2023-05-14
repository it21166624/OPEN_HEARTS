package com.example.madprojects

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.madproject.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Home3 : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home4)

        etEmail = findViewById(R.id.editTextTextEmailAddress3)
        etPassword = findViewById(R.id.editTextTextPassword3)

        dbRef = FirebaseDatabase.getInstance().getReference("User")

        val button5 = findViewById<Button>(R.id.button5)
        button5.setOnClickListener {

            var isDetailsValid = false

            if(etPassword.text.isEmpty()){

                etPassword.error = "Password must be entered"
                isDetailsValid = true

            }else if(!(passwordValidation(etPassword))){

                etPassword.error = "Password must be at least 8 characters long and contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=)"
                isDetailsValid = true
            }


            if(etEmail.text.isEmpty()){

                etEmail.error = "Email must be entered"
                isDetailsValid = true

            }else if(!(emailValidation(etEmail.text.toString()))){

                etEmail.error = "Invalid email format"
                isDetailsValid = true

            }

            if (!(isDetailsValid)){
                validateData()
                Log.d("TAG", "onCreate: " + "Something is good")

            }else{
                Log.d("TAG", "onCreate: " + "Something went wrong")

            }
        }


    }

    private fun emailValidation(email : String) : Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    //password validation function
    private fun passwordValidation(password: EditText): Boolean {
        //password validation
        val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")

        val passwordVal = password.text.toString()

        return passwordVal.matches(passwordPattern)
    }

    private fun validateData() {

        dbRef = FirebaseDatabase.getInstance().getReference("User")

        var emailCount = 0
        var foundUser : UserModel? = null
        val userEmailAdd = etEmail.text.toString()
        val userPass = etPassword.text.toString()

        dbRef.addValueEventListener(object  : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){

                    for(userSnap in snapshot.children){

                        val userData = userSnap.getValue(UserModel::class.java)

                        if (userData != null) {

                            if(userEmailAdd == userData.Email.toString()){
                                emailCount += 1
                                foundUser = userData
                                foundUser?.Email?.let { Log.d("TAG", it) }
                            }
                        }
                    }
                    if(emailCount > 0){

                        if(userEmailAdd == foundUser?.Email.toString()){

                            etEmail.error = null

                            if (userPass == foundUser?.Password.toString()){

                                etPassword.error = null

                                //toast message to success full login
                                Toast.makeText(applicationContext,"Successfully logged in", Toast.LENGTH_SHORT).show()

                                //Intent to dashboard
                                val intent = Intent(applicationContext, Home::class.java)



                                startActivity(intent)

                            }else{
                                //hidingProgressbar

                                etPassword.error = "Password is incorrect"
                            }
                        }
                    }else{
                        etEmail.error = "This email address is not registere"
                        //hidingProgressbar

                    }
                    Log.d(TAG, "loadUserData()details $foundUser")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}






