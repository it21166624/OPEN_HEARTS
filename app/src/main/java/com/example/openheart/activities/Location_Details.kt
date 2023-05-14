package com.example.openheart.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.openheart.R
import com.example.openheart.models.LocationModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase

class Location_Details : AppCompatActivity() {

    private lateinit var tvLocId: TextView
    private lateinit var tvLocName: TextView
    private lateinit var tvLocAddress: TextView
    private lateinit var tvLocNumber: TextView
    private lateinit var tvLocMap: TextView

    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_details)

        initView()
        setValuesToView()

        btnUpdate = findViewById(R.id.btnUpdate) // Initialize btnUpdate
        btnDelete = findViewById(R.id.btnDelete) // Initialize btnUpdate

        btnUpdate.setOnClickListener {
            openUpdateForm(
                intent.getStringExtra("locId").toString()
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("locId").toString()
            )
        }
    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("location").child(id)
        dbRef.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Location Data Deleted", Toast.LENGTH_LONG).show()

                val intent = Intent(this, Fetching_Data::class.java)
                finish()
                startActivity(intent)
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, "Error deleting location data: ${error.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun initView() {
        tvLocId = findViewById(R.id.tvLocId)
        tvLocName = findViewById(R.id.tvLocName)
        tvLocAddress = findViewById(R.id.tvLocAddress)
        tvLocNumber = findViewById(R.id.tvLocNumber)
        tvLocMap = findViewById(R.id.tvLocMap)
    }

    private fun setValuesToView() {
        tvLocId.text = intent.getStringExtra("locId")
        tvLocName.text = intent.getStringExtra("locName")
        tvLocAddress.text = intent.getStringExtra("locAddress")
        tvLocNumber.text = intent.getStringExtra("locNum")
        tvLocMap.text = intent.getStringExtra("locMap")
    }

    private fun openUpdateForm(locId: String) {
        val LDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val LDialogView = inflater.inflate(R.layout.activity_update_form, null)

        LDialog.setView(LDialogView)

        val UpdateName = LDialogView.findViewById<TextInputLayout>(R.id.UpdateLocNameContainer)
        val UpdateAddress = LDialogView.findViewById<TextInputLayout>(R.id.UpdateLocAddressContainer)
        val UpdateNumber = LDialogView.findViewById<TextInputLayout>(R.id.UpdateLocPhoneContainer)
        val UpdateMap = LDialogView.findViewById<TextInputLayout>(R.id.UpdateLocMapViewContainer)
        val btnUpdateButton = LDialogView.findViewById<MaterialButton>(R.id.btnUpdate)

        // set data to edit text
        UpdateName.editText?.setText(tvLocName.text)
        UpdateAddress.editText?.setText(tvLocAddress.text)
        UpdateNumber.editText?.setText(tvLocNumber.text)
        UpdateMap.editText?.setText(tvLocMap.text)

        LDialog.setTitle("Updating $locId Record")

        val alertDialog = LDialog.create()
        alertDialog.show()

        btnUpdateButton.setOnClickListener {
            updateLocationData(
                locId,
                UpdateName.editText?.text.toString(),
                UpdateAddress.editText?.text.toString(),
                UpdateNumber.editText?.text.toString(),
                UpdateMap.editText?.text.toString()
            )

            Toast.makeText(applicationContext, "Location Data Updated", Toast.LENGTH_LONG).show()

            // setting updated data to our TextViews
            tvLocName.text = UpdateName.editText?.text.toString()
            tvLocAddress.text = UpdateAddress.editText?.text.toString()
            tvLocNumber.text = UpdateNumber.editText?.text.toString()
            tvLocMap.text = UpdateMap.editText?.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateLocationData(
        id: String,
        Name: String?,
        Address: String?,
        Number: String?,
        Map: String?
    ) {
        // create database reference
        val dbRef = FirebaseDatabase.getInstance().getReference("location").child(id)
        val locInfo = LocationModel(id, Name.orEmpty(), Address.orEmpty(), Number.orEmpty(), Map.orEmpty())
        dbRef.setValue(locInfo)
    }


}

