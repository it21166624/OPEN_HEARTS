package com.example.openheart.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openheart.R
import com.example.openheart.adapters.LocationAdapter
import com.example.openheart.models.LocationModel
import com.google.firebase.database.*

import com.google.firebase.ktx.Firebase

class Fetching_Data : AppCompatActivity() {

    private lateinit var LocRecycleView : RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var locList: ArrayList<LocationModel>
    private lateinit var dbRef: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching_data)

        LocRecycleView = findViewById(R.id.rvLocation)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        LocRecycleView.layoutManager = LinearLayoutManager(this)
        LocRecycleView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        locList = arrayListOf<LocationModel>()

        getLocationData()

    }

    private fun getLocationData() {
        LocRecycleView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        // database reference
        dbRef = FirebaseDatabase.getInstance().getReference("location")

        //get data
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                locList.clear()
                if(snapshot.exists()){
                    for (locSnap in snapshot.children){
                        val locData = locSnap.getValue(LocationModel::class.java)
                        locList.add(locData!!)
                    }
                    val LAdapter = LocationAdapter(locList)
                    LocRecycleView.adapter = LAdapter

                    LAdapter.setOnLocationClickListener(object : LocationAdapter.onLocationClickListner{
                        override fun onLocationClick(position: Int) {
                            val intent = Intent( this@Fetching_Data, Location_Details::class.java)

                            //put extras
                            intent.putExtra("locId", locList[position].locId)
                            intent.putExtra("locName", locList[position].locName)
                            intent.putExtra("locAddress", locList[position].locAddress)
                            intent.putExtra("locNum", locList[position].locNum)
                            intent.putExtra("locMap", locList[position].locMap)
                            startActivity(intent)

                        }

                    })

                    LocRecycleView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        val searchView = findViewById<SearchView>(R.id.SearchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                performSearch(newText)
                return true
            }
        })
    }

    private fun performSearch(query: String) {
        val filteredList = locList.filter { location ->
            location.locName?.contains(query, ignoreCase = true) == true
        }
        (LocRecycleView.adapter as? LocationAdapter)?.submitList(filteredList)

    }
}