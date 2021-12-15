package com.android.moscow4D.fragments.home

import PlaceViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.InvalidationTracker
import com.android.moscow4D.MainActivity
import com.android.moscow4D.R
import com.android.moscow4D.database.PlaceListAdapter
import com.android.moscow4D.databinding.FragmentHomeBinding
import kotlinx.coroutines.InternalCoroutinesApi


class HomeFragment(mainActivity: MainActivity) : Fragment() {

    //private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!

    @InternalCoroutinesApi
    private lateinit var placeViewModel: PlaceViewModel

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
         /*
         * _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val prefs = PreferenceManager
            .getDefaultSharedPreferences(context)

        val name = prefs.getString(
            "username", ""
        )

        binding.apply{
            binding.username.text = name
        }*/

        // Recyclerview
        val adapter = PlaceListAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvItemsList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        placeViewModel.allPlaces.observe(viewLifecycleOwner,
            Observer { place ->
                adapter.setData(place)
            })


        view.findViewById<Button>(com.android.moscow4D.R.id.btnAdd).setOnClickListener {
            mAct.setFragment(addFragment)
        }

        return view
    }

    //override fun onDestroyView() {
    //    super.onDestroyView()
    //    _binding = null
    //}
}