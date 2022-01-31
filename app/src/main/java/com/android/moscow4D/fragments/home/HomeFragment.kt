package com.android.moscow4D.fragments.home

import PlacesViewModel
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.moscow4D.ColorThemeController
import com.android.moscow4D.MainActivity
import com.android.moscow4D.R
import com.android.moscow4D.database.PlaceListAdapter
import com.android.moscow4D.databinding.FragmentSettingsBinding
import com.android.moscow4D.viewmodels.SettingsViewModel


class HomeFragment : Fragment(), PlaceListAdapter.OnItemClickListener{

    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    private lateinit var placesViewModel: PlacesViewModel
    private val settingsViewModel: SettingsViewModel by activityViewModels()

    //val mAct = MainActivity()
    private val addFragment = AddFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        val view = inflater.inflate(com.android.moscow4D.R.layout.fragment_home, container, false)

        // Update from settings
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        settingsViewModel.username.observe(viewLifecycleOwner, {username->
            view?.findViewById<TextView>(R.id.username)!!.text = username
        })

        // UserViewModel
        placesViewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)
        placesViewModel.initList()

        // Recyclerview
        val adapter = PlaceListAdapter(placesViewModel.allPlacesList, this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvItemsList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        placesViewModel.allPlaces.observe(viewLifecycleOwner,
            Observer { places ->
                adapter.setData(places)
            })

        view.findViewById<Button>(com.android.moscow4D.R.id.btnAdd).setOnClickListener {
            //mAct.setFragment(addFragment)
            requireActivity().supportFragmentManager.inTransaction {
                replace(R.id.fragment_container, addFragment)
            }
        }

        return view
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    override fun onItemClick(position: Int) {
        val place = placesViewModel.get(position)
        val intent = Intent(context, PlacePageActivity::class.java)
        intent.putExtra("selected_place", place)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}