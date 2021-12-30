package com.android.moscow4D.fragments.settings

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.preference.EditTextPreference
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.android.moscow4D.databinding.FragmentSettingsBinding
import com.android.moscow4D.R
import com.android.moscow4D.fragments.shared.SharedViewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)

        preferenceManager.findPreference<EditTextPreference>("username")!!.setOnPreferenceChangeListener{ preference, newValue ->
            sharedViewModel.SaveUsername(newValue.toString())
            // Reflect the newValue to Preference?
            true
        }
    }
}