package com.android.moscow4D.fragments.home

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.moscow4D.CacheEngine
import com.android.moscow4D.R


class PlacePageActivity: AppCompatActivity() {
    /**
     * Class of activity with info about place.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.place_page_layout)

        val place_name: TextView = findViewById<TextView>(R.id.place_page_name)  // Get name TextView.
        val place_image: ImageView = findViewById<ImageView>(R.id.place_page_image)  // Get ImageView.
        val place_description: TextView = findViewById<TextView>(R.id.place_page_description)  // Get description TextView.

        val pos: Int = intent.getIntExtra("position", 0)  // Get position in CacheEngine data.

        place_name.text = CacheEngine.data[pos]["Place"]  // Set place name from CacheEngine data.
        place_description.text = CacheEngine.data[pos]["Description"]  // Set description.
        place_image.setImageResource(CacheEngine.data[pos]["Image"]!!.toInt())  // Set image.
    }
}
