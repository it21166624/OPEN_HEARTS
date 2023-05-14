package com.example.openheart.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.openheart.models.LocationModel
import com.example.openheart.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNewLocation : AppCompatActivity() {

    private lateinit var locationName: TextInputEditText
    private lateinit var locationAddress: TextInputEditText
    private lateinit var locationNumber: TextInputEditText
    private lateinit var locationMap: TextInputEditText
    private lateinit var addLocation: Button

    private lateinit var dbRef: DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_location)




        locationName = findViewById(R.id.LocationNameEditText)
        locationAddress = findViewById(R.id.LocationAddressEditText)
        locationNumber = findViewById(R.id.LocationPhoneEditText)
        locationMap = findViewById(R.id.LocationMapEditText)
        addLocation = findViewById(R.id.button12)

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
        val locMap = locationMap.text.toString()

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

        if (locNum.toString().length < 10) {
            locationNumber.error = "Please Enter 10 Numbers"
        }

        val locId = dbRef.push().key!!

        val location = LocationModel(locId, locName, locAddress, locNum, locMap)

        dbRef.child(locId).setValue(location)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                locationName.text?.clear()
                locationAddress.text?.clear()
                locationNumber.text?.clear()
                locationMap.text?.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }



}