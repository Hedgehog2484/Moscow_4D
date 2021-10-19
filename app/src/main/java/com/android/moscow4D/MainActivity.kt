package com.android.moscow4D

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.moscow4D.fragments.home.HomeFragment
import com.android.moscow4D.fragments.map.MapController
import com.android.moscow4D.fragments.map.MapsFragment
import com.android.moscow4D.fragments.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity: AppCompatActivity() {
    private val mapsFragment = MapsFragment(this)
    private val homeFragment = HomeFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun setFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}