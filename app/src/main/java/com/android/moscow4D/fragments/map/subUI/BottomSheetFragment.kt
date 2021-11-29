package com.android.moscow4D.fragments.map.subUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.moscow4D.CacheEngine
import android.widget.Toast
import com.android.moscow4D.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment: BottomSheetDialogFragment() {
    /**
     * This class represents bottom sheet xml and its logic
     * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val place_name: TextView = view.findViewById<TextView>(R.id.bottom_sheet_title)  // Get name TextView.
        val place_image: ImageView = view.findViewById<ImageView>(R.id.bottom_sheet_image)  // Get ImageView.
        val place_description: TextView = view.findViewById<TextView>(R.id.bottom_sheet_description)  // Get description TextView.

        val pos: Int = 0  // Get position in CacheEngine data.

        place_name.text = CacheEngine.data[pos]["Place"]  // Set place name from CacheEngine data.
        place_description.text = CacheEngine.data[pos]["Description"]  // Set description.
        place_image.setImageResource(CacheEngine.data[pos]["Image"]!!.toInt())  // Set image.
    }

}