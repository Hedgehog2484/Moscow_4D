package com.android.moscow4D.fragments.home


import android.graphics.Color
import android.os.Build
import com.android.moscow4D.R
import androidx.fragment.app.ListFragment

import android.os.Bundle
import android.widget.Toast

import android.widget.AdapterView.OnItemClickListener


import android.widget.SimpleAdapter

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.view.isNotEmpty
import com.google.android.material.textfield.TextInputLayout


class PlacesListFragment : ListFragment() {
/**
 * This class sets images and places names to the ListFragment (Home page).
 */

    // Array of places names.
    var places = arrayOf("")

    // Array of places images (only 85x85 pixels).
    var images = intArrayOf()

    var adapter: SimpleAdapter? = null
    var data = ArrayList<HashMap<String, String?>>()  // Map of places and pictures.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * Adds values to the data map and sets value to the adapter.
         * Sets the action when the search button was clicked.
         */

        // Creating temporary map.
        var map = HashMap<String, String?>()

        // Fill map and than adds it into data map.
        for (i in places.indices) {
            map = HashMap()
            map["Place"] = places[i]
            map["Image"] = images[i].toString()
            data.add(map)
        }

        // Keys in map.
        val from = arrayOf("Place", "Image")

        // IDs of views.
        val to = intArrayOf(R.id.place_name, R.id.place_image)

        // Adapter.
        adapter = SimpleAdapter(requireActivity(), data, R.layout.home_places_list_model, from, to)
        listAdapter = adapter

        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onStart() {
        /**
         * Renders listView and shows it in the fragment.
         */
        super.onStart()
        listView.divider = null  // Turn off background between list items.
        listView.dividerHeight = 50  // Set distance between list items.

        listView.onItemClickListener =
            OnItemClickListener { av, v, pos, id ->
                Toast.makeText(requireActivity(), data[pos]["Place"], Toast.LENGTH_SHORT).show()  // Show ListView.
            }
    }
}