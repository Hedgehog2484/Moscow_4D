package com.android.moscow4D

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.android.moscow4D.fragments.home.AddFragment
import com.android.moscow4D.fragments.home.HomeFragment
import com.android.moscow4D.fragments.map.MapsFragment
import com.android.moscow4D.fragments.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.android.moscow4D.databinding.ActivityMainBinding


class MainActivity: AppCompatActivity() {
    private val mapsFragment = MapsFragment(this, supportFragmentManager)
    private val homeFragment = HomeFragment(this)
    private val settingsFragment = SettingsFragment()

    private lateinit var binding: ActivityMainBinding

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_main)


        setFragment(homeFragment)

        findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.ic_map -> setFragment(mapsFragment)
                    R.id.ic_home -> setFragment(homeFragment)
                    R.id.ic_settings -> setFragment(settingsFragment)
                }
                true
            }

    }

     fun setFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack("tag").commit()
    }
}