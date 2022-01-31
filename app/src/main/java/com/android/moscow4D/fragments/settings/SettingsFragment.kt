package com.android.moscow4D.fragments.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Log.ERROR
import android.util.Log.WARN
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.activityViewModels
import androidx.preference.*
import com.android.moscow4D.ColorThemeController
import com.android.moscow4D.R
import com.android.moscow4D.viewmodels.SettingsViewModel
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    private val sharedViewModel: SettingsViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)

        preferenceManager.findPreference<EditTextPreference>("username")!!.setOnPreferenceChangeListener { preference, newValue ->
            sharedViewModel.saveUsername(newValue.toString())
            // Reflect the newValue to Preference?
            true
        }

        preferenceManager.findPreference<ListPreference>("color_theme")!!.setOnPreferenceChangeListener { preference, newValue ->
            ColorThemeController.setColorTheme(requireContext())
            requireActivity().recreate()
            true
        }

        preferenceManager.findPreference<ListPreference>("language")!!.setOnPreferenceChangeListener { preference, newValue ->
            requireActivity().recreate()
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("CURRENT_FRAGMENT", "settings")
        super.onSaveInstanceState(outState)
    }
}