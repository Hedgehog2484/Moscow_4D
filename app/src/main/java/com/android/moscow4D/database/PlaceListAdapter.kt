package com.android.moscow4D.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.moscow4D.R
import java.util.Collections.emptyList
import com.android.moscow4D.database.PlaceEntity

class PlaceListAdapter: RecyclerView.Adapter<PlaceListAdapter.MyViewHolder>() {

    private var placeList = emptyList<PlaceEntity>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_places_list_model, parent, false))
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = placeList[position]
        holder.itemView.findViewById<TextView>(R.id.tvName).text = currentItem.place_name
    }

    fun setData(place: List<PlaceEntity>){
        this.placeList = place
        notifyDataSetChanged()
    }
}