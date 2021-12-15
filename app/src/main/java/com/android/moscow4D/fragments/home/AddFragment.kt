package com.android.moscow4D.fragments.home

import PlaceViewModel
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.moscow4D.R
import com.android.moscow4D.database.PlaceEntity
import kotlinx.coroutines.InternalCoroutinesApi

class AddFragment : Fragment() {

    @InternalCoroutinesApi
    private lateinit var placeViewModel: PlaceViewModel

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        view.findViewById<Button>(R.id.add_btn)
            .setOnClickListener {
                insertData()
            }

        return view
    }

    @InternalCoroutinesApi
    private fun insertData() {
        val placeId = view?.findViewById<EditText>(R.id.addPlaceId_et)?.text.toString().toUInt().toInt()
        val placeType = view?.findViewById<EditText>(R.id.addPlaceType_et)?.text.toString()
        val placeName = view?.findViewById<EditText>(R.id.addPlaceName_et)?.text.toString()
        val placeDesc = view?.findViewById<EditText>(R.id.addPlaceDesc_et)?.text.toString()

        if(inputCheck(placeName)){
            // Create User Object
            val place = PlaceEntity(placeId, placeType, placeName, placeDesc, "3", "4", "5")
            // Add Data to Database
            placeViewModel.insert(place)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate Back
            getFragmentManager()?.popBackStack()
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String): Boolean{
        return !TextUtils.isEmpty(firstName)
    }
}