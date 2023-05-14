package com.example.openheart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.openheart.R
import com.example.openheart.models.LocationModel

class LocationAdapter(private val locList: ArrayList<LocationModel>) :
    RecyclerView.Adapter<LocationAdapter.ViewHolder>(){

    private lateinit var LocListener: onLocationClickListner

    interface onLocationClickListner{
        fun onLocationClick(position: Int)
    }

    fun setOnLocationClickListener(clickListner: onLocationClickListner){
        LocListener = clickListner
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_location_list_item,parent,false)
        return ViewHolder(itemView, LocListener)
    }

    override fun onBindViewHolder(holder: LocationAdapter.ViewHolder, position: Int) {
        val currentLocation = locList[position]

        holder.tvLocationName.text = currentLocation.locName

    }



    override fun getItemCount(): Int {
        return locList.size
    }

    fun submitList(filteredList: List<LocationModel>) {

    }

    class ViewHolder(itemView: View, clickListener: onLocationClickListner) : RecyclerView.ViewHolder(itemView) {

        val tvLocationName : TextView = itemView.findViewById(R.id.tvLocationName)


        // pass data to the position
        init {
            itemView.setOnClickListener{
                clickListener.onLocationClick(adapterPosition)
            }
        }
    }

}