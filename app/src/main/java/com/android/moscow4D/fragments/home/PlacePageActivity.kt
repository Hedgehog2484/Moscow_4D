package com.android.moscow4D.fragments.home

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.moscow4D.R

import com.android.moscow4D.database.PlaceEntity


class PlacePageActivity: AppCompatActivity() {
    /**
     * Class of activity with info about place.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.place_page_layout)

        val place = intent.getParcelableExtra<PlaceEntity>("selected_place")

        val placeName: TextView = findViewById<TextView>(R.id.place_page_name)  // Get name TextView.
        val placeImage: ImageView = findViewById<ImageView>(R.id.place_page_image)  // Get ImageView.
        val placeDescription: TextView = findViewById<TextView>(R.id.place_page_description)  // Get description TextView.

        //placeName.text = CacheEngine.data[pos]["Place"]  // Set place name from CacheEngine data.
        //placeDescription.text = CacheEngine.data[pos]["Description"]  // Set description.
        //placeImage.setImageResource(CacheEngine.data[pos]["Image"]!!.toInt())  // Set image.

        if (place != null) {
            placeName.text = place.place_name
            placeDescription.text = place.place_description
        }
    }
}
