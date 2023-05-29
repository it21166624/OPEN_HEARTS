package com.example.amaya

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProjectAdapter(private val projectList: ArrayList<ProjectModel>) : RecyclerView.Adapter<ProjectAdapter.ViewHolder>(){
    private lateinit var pListener: onItemClickListener  //ClickListener

    interface onItemClickListener {             //ClickListener
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {        //ClickListener
        pListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_projects, parent, false)
        return ViewHolder(itemView, pListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProject = projectList[position]
        holder.tvProjectName.text = currentProject.projectName
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvProjectName: TextView = itemView.findViewById(R.id.tvProjectName)

        init {          //ClickListener
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }
}