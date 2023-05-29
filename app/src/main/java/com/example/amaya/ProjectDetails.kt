package com.example.amaya

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class ProjectDetails : AppCompatActivity() {

    private lateinit var tvProjId : TextView          //ClickListener
    private lateinit var tvProjectName : TextView
    private lateinit var tvOrganizationName : TextView
    private lateinit var tvDescription : TextView


    private lateinit var updateProject: Button
    private lateinit var deleteProject: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_details)

        initView()      //ClickListener
        setValuesToViews()

        updateProject.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("projectId").toString(),
                intent.getStringExtra("projectName").toString()
            )
        }

        deleteProject.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("projectId").toString()
            )
        }
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Projects").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Project data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ProjectView::class.java)
            finish()
            startActivity(intent)
        }
        mTask.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView(){

        tvProjId = findViewById(R.id.tvProjId)
        tvProjectName = findViewById(R.id.tvProjectName)
        tvOrganizationName = findViewById(R.id.tvOrganizationName)
        tvDescription = findViewById(R.id.tvDescription)

        updateProject = findViewById(R.id.updateProject)
        deleteProject = findViewById(R.id.deleteProject)
    }

    private fun setValuesToViews() {

        tvProjId.text = intent.getStringExtra("projectId")
        tvProjectName.text = intent.getStringExtra("projectName")
        tvOrganizationName.text = intent.getStringExtra("organizationName")
        tvDescription.text = intent.getStringExtra("description")

    }


    private fun openUpdateDialog(       //Update
        projectId : String,
        projectName : String

    ) {
        val pDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val pDialogView = inflater.inflate(R.layout.activity_update_project, null)

        pDialog.setView(pDialogView)

        val etProjectName2 = pDialogView.findViewById<EditText>(R.id.etProjectName2)
        val etOrganizationName2 = pDialogView.findViewById<EditText>(R.id.etOrganizationName2)
        val etDescription2 = pDialogView.findViewById<EditText>(R.id.etDescription2)

        val updateProject2 = pDialogView.findViewById<Button>(R.id.updateProject2)

        etProjectName2.setText(intent.getStringExtra("projectName").toString())
        etOrganizationName2.setText(intent.getStringExtra("organizationName").toString())
        etDescription2.setText(intent.getStringExtra("description").toString())


        pDialog.setTitle("Updating $projectName Record")

        val alertDialog = pDialog.create()
        alertDialog.show()

        updateProject2.setOnClickListener {
            updateProjectData(
                projectId,
                etProjectName2.text.toString(),
                etOrganizationName2.text.toString(),
                etDescription2.text.toString()
            )

            Toast.makeText(applicationContext, "Student Data Updated", Toast.LENGTH_LONG).show()

            //We are setting updated data to our TextViews
            tvProjectName.text = etProjectName2.text.toString()
            tvOrganizationName.text = etOrganizationName2.text.toString()
            tvDescription.text = etDescription2.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateProjectData(
        id: String,
        nameP: String,
        nameO: String,
        descriptions: String


    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Projects").child(id)
        val projectInfo = ProjectModel(id, nameP, nameO, descriptions)
        dbRef.setValue(projectInfo)
    }

}