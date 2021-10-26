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
        val to = intArrayOf(R.id.place_name, R.id.place_image)

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
        listView.divider = null  // Turn off background between list items.
        listView.dividerHeight = 50  // Set distance between list items.

        listView.onItemClickListener =
            OnItemClickListener { av, v, pos, id ->
                val place_name = CacheEngine.data[pos]["Place"]  // Get place name.

                val intent = Intent(context, PlacePageActivity::class.java).apply{
                    putExtra("position", pos)  // Passing place position in list to the place info activity.
                 }

                context?.startActivity(intent)  // Starting new activity with info about place.

                // Toast.makeText(requireActivity(), data[pos]["Place"], Toast.LENGTH_SHORT).show()  // Show ListView.
            }
    }
}