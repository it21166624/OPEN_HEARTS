package com.example.amaya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddProject : AppCompatActivity() {

    private lateinit var etProjectName : EditText
    private lateinit var etOrganizationName : EditText
    private lateinit var etDescription : EditText

    private lateinit var btnAddProject1: Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)

        etProjectName = findViewById(R.id.etProjectName)
        etOrganizationName = findViewById(R.id.etOrganizationName)
        etDescription = findViewById(R.id.etDescription)

        btnAddProject1 = findViewById(R.id.btnAddProject1)

        dbRef = FirebaseDatabase.getInstance().getReference("Projects")

        btnAddProject1.setOnClickListener {
            saveProjectData()
        }
    }

    private fun saveProjectData(){
        //Getting Values From Edit text and Storing them in variables.
        val projectName = etProjectName.text.toString()
        val organizationName = etOrganizationName.text.toString()
        val description = etDescription.text.toString()

        if (projectName.isEmpty()){
            etProjectName.error = "Please Enter Project Name"
        }
        if(organizationName.isEmpty()){
            etOrganizationName.error = "Please Enter Organization Name"
        }
        if(description.isEmpty()){
            etDescription.error = "Please Enter Description"
        }

        val projectId = dbRef.push().key!!

        val project = ProjectModel(projectId, projectName, organizationName, description)

        dbRef.child(projectId).setValue(project)
            .addOnCompleteListener{
                Toast.makeText(this, "Project Inserted", Toast.LENGTH_LONG).show()

                    etProjectName.text.clear()
                    etOrganizationName.text.clear()
                    etDescription.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}