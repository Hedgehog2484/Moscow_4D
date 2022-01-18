package com.android.moscow4D.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.moscow4D.R
import java.util.Collections.emptyList
import com.android.moscow4D.database.PlaceEntity

class PlaceListAdapter(
    private val allPlaces: List<PlaceEntity>,
    private val onClickListener: OnItemClickListener): RecyclerView.Adapter<PlaceListAdapter.MyViewHolder>() {

    private var placeList = emptyList<PlaceEntity>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position:Int = adapterPosition

            if (position != RecyclerView.NO_POSITION)
                onClickListener.onItemClick(position)
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_places_list_model, parent, false))
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = placeList[position]

        holder.itemView.findViewById<TextView>(R.id.tvName).text = currentItem.place_name
        holder.itemView.findViewById<TextView>(R.id.tvDesc).text = currentItem.place_description

        //holder.itemView.findViewById<ImageButton>(R.id.ivLead).setOnClickListener()
        /*holder.itemView.setOnClickListener { place ->
            onClickListener.invoke(place, currentItem)
        }*/
    }

    fun setData(places: List<PlaceEntity>){
        this.placeList = places
        notifyDataSetChanged()
    }
}