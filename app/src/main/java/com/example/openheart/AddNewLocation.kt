package com.example.openheart

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNewLocation : AppCompatActivity() {

    private lateinit var locationName: EditText
    private lateinit var locationAddress: EditText
    private lateinit var locationNumber: EditText
    private lateinit var addLocation: Button

    private lateinit var dbRef: DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_location)



        locationName = findViewById(R.id.editTextLocationName)
        locationAddress = findViewById(R.id.editTextLocationAddress)
        locationNumber = findViewById(R.id.editTextPhone)
        addLocation = findViewById(R.id.button9)

        dbRef = FirebaseDatabase.getInstance().getReference("location")

        addLocation.setOnClickListener {

            saveLocationData()
        }
    }

    //
    private fun saveLocationData() {
        // getting values
        val locName = locationName.text.toString()
        val locAddress = locationAddress.text.toString()
        val locNum = locationNumber.text.toString()

        // add validations
        if (locName.isEmpty()) {
            locationName.error = "please add location name"
        }

        if (locAddress.isEmpty()) {
            locationAddress.error = "please add location Address"
        }

        if (locNum.isEmpty()) {
            locationNumber.error = "please add location number"
        }

        val locId = dbRef.push().key!!

        val location = LocationModel(locId, locName, locAddress)

        dbRef.child(locId).setValue(location)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

            locationName.text.clear()
            locationAddress.text.clear()
            locationNumber.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

}