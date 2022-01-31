package com.android.moscow4D

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.android.moscow4D.fragments.home.HomeFragment
import com.android.moscow4D.fragments.map.MapsFragment
import com.android.moscow4D.fragments.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.android.moscow4D.databinding.ActivityMainBinding
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*
import androidx.preference.PreferenceManager


class MainActivity: AppCompatActivity() {
    private val mapsFragment = MapsFragment(this, supportFragmentManager)
    private val homeFragment = HomeFragment()
    private val settingsFragment = SettingsFragment()

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        ColorThemeController.setColorTheme(this) // Set color theme when app was started.
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        changeLang(PreferenceManager.getDefaultSharedPreferences(this))

        findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.ic_map -> setFragment(mapsFragment)
                    R.id.ic_home -> setFragment(homeFragment)
                    R.id.ic_settings -> setFragment(settingsFragment)
                }
                true
            }

        if (savedInstanceState?.getInt("current_fragment") == R.id.ic_settings){
            Log.i("BEBRA", "bebra")
            setFragment(settingsFragment)
        }else setFragment(homeFragment)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        changeLang(PreferenceManager.getDefaultSharedPreferences(this))
        if (savedInstanceState.getString("CURRENT_FRAGMENT") == "settings") setFragment(settingsFragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("current_fragment", findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId)
    }

     private fun setFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack("tag").commit()
    }

    private fun changeLang(sp: SharedPreferences) {
        lateinit var language: String
        when (sp.getString("language", "1")!!) {
            "1" -> language = "ru"
            "2" -> language = "en"
        }
        val config = Configuration()
        Locale.setDefault(Locale(language))
        getSharedPreferences("def_loc", 0).edit().putString("Language", language).apply()
        config.locale = Locale(language)
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )

        startActivity(intent)
    }
}