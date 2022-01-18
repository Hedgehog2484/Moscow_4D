package com.android.moscow4D.fragments.settings

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceFragmentCompat
import com.android.moscow4D.R
import com.android.moscow4D.viewmodels.SettingsViewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val sharedViewModel: SettingsViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)

        preferenceManager.findPreference<EditTextPreference>("username")!!.setOnPreferenceChangeListener{ preference, newValue ->
            sharedViewModel.SaveUsername(newValue.toString())
            // Reflect the newValue to Preference?
            true
        }
    }
}