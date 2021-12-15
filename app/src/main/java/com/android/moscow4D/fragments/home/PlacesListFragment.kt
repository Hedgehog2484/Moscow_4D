package com.android.moscow4D.fragments.home


import android.content.Intent
import com.android.moscow4D.R
import androidx.fragment.app.ListFragment

import android.os.Bundle

import android.widget.AdapterView.OnItemClickListener


import android.widget.SimpleAdapter

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.android.moscow4D.fragments.home.PlacePageActivity

import com.android.moscow4D.CacheEngine


class PlacesListFragment : ListFragment() {
/**
 * This class sets images and places names to the ListFragment (Home page).
 */
    var adapter: SimpleAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * Adds values to the data map and sets value to the adapter.
         * Sets the action when the search button was clicked.
         */

        // Keys in map.
        val from = arrayOf("Place", "Image")

        // IDs of views.
        val to = intArrayOf(R.id.tvName, R.id.place_image)

        // Adapter.
        adapter = SimpleAdapter(requireActivity(), CacheEngine.data, R.layout.home_places_list_model, from, to)
        listAdapter = adapter

        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onStart() {
        /**
         * Renders listView and shows it in the fragment.
         */
        super.onStart()
        listView.isDrawSelectorOnTop = true;
        listView.divider = null  // Turn off background between list items.
        listView.dividerHeight = 50  // Set distance between list items.
    }
}