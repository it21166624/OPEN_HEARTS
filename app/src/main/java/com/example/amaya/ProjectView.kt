package com.example.amaya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProjectView : AppCompatActivity() {

    private lateinit var addProject2 : Button

    private lateinit var projectRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var projectList : ArrayList<ProjectModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_view)

        addProject2 = findViewById(R.id.addProject2)

        addProject2.setOnClickListener {
            val intent = Intent(this, AddProject::class.java)
            startActivity(intent)
        }

        projectRecyclerView = findViewById(R.id.rvProject)
        projectRecyclerView.layoutManager = LinearLayoutManager(this)
        projectRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        projectList = arrayListOf<ProjectModel>()

        getProjectData()

    }

    private fun getProjectData(){

        projectRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Projects")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                projectList.clear()
                if(snapshot.exists()){
                    for(projectSnap in snapshot.children){
                        val projectData = projectSnap.getValue(ProjectModel::class.java)
                        projectList.add(projectData!!)
                    }
                    val pAdapter = ProjectAdapter(projectList)
                    projectRecyclerView.adapter = pAdapter

                    pAdapter.setOnItemClickListener(object : ProjectAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@ProjectView, ProjectDetails::class.java) //ClickListener

                            //Put Extra Data
                            intent.putExtra("projectId", projectList[position].projectId)
                            intent.putExtra("projectName", projectList[position].projectName)
                            intent.putExtra("organizationName", projectList[position].organizationName)
                            intent.putExtra("description", projectList[position].description)


                            startActivity(intent)
                        }

                    })

                    projectRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}