package com.android.moscow4D.fragments.home

import PlaceViewModel
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.compose.runtime.*
import androidx.room.InvalidationTracker
import com.android.moscow4D.MainActivity
import com.android.moscow4D.R
import com.android.moscow4D.database.PlaceEntity
import com.android.moscow4D.database.PlaceListAdapter
import com.android.moscow4D.databinding.FragmentHomeBinding
import com.android.moscow4D.databinding.FragmentSettingsBinding
import com.android.moscow4D.fragments.shared.SharedViewModel
import kotlinx.coroutines.InternalCoroutinesApi


class HomeFragment(mainActivity: MainActivity) : Fragment(), PlaceListAdapter.OnItemClickListener{

    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    @InternalCoroutinesApi
    private lateinit var placeViewModel: PlaceViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()

    val mAct = mainActivity
    val addFragment = AddFragment()

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        val view = inflater.inflate(com.android.moscow4D.R.layout.fragment_home, container, false)

        // Update from settings
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        sharedViewModel.username.observe(viewLifecycleOwner, {username->
            view?.findViewById<TextView>(R.id.username)!!.text = username
        })

        // UserViewModel
        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        placeViewModel.initList()

        // Recyclerview
        val adapter = PlaceListAdapter(placeViewModel.allPlacesList, this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvItemsList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        placeViewModel.allPlaces.observe(viewLifecycleOwner,
            Observer { places ->
                adapter.setData(places)
            })

        view.findViewById<Button>(com.android.moscow4D.R.id.btnAdd).setOnClickListener {
            mAct.setFragment(addFragment)
        }

        return view
    }

    @InternalCoroutinesApi
    override fun onItemClick(position: Int) {
        val place = placeViewModel.get(position)
        val intent = Intent(context, PlacePageActivity::class.java)
        intent.putExtra("selected_place", place)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}