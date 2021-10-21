package com.android.moscow4D.fragments.settings

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.moscow4D.R


class SettingsFragment : Fragment() {

    private val prefs_file_name: String = "settings"  // The name of preferences file.

    private lateinit var lang_radio_group: RadioGroup
    private lateinit var rus_lang_btn: RadioButton
    private lateinit var eng_lang_btn: RadioButton

    private lateinit var male_voice_btn: RadioButton
    private lateinit var female_voice_btn: RadioButton

    private lateinit var theme_radio_group: RadioGroup
    private lateinit var lite_theme_btn: RadioButton
    private lateinit var dark_theme_btn: RadioButton

    private lateinit var prefs: SharedPreferences
    private lateinit var prefs_values: Map<String, String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = requireActivity().getSharedPreferences(prefs_file_name, Context.MODE_PRIVATE)  // Get prefs to the var, private mode - only this app can use file.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onPause() {
        super.onPause()

        save_settings()
    }

    override fun onResume() {
        super.onResume()

        load_settings()

        lang_radio_group = requireActivity().findViewById(R.id.radioGroup_language)  // Initialize language radio group.
        lang_radio_group.setOnCheckedChangeListener {
                _, checkedId -> requireActivity().findViewById<RadioButton>(checkedId)?.apply {
            Toast.makeText(context, "Перезапустите приложение, чтобы изменения настроек языка вступили в силу.", Toast.LENGTH_SHORT).show()
            }
        }

        theme_radio_group = requireActivity().findViewById(R.id.radioGroup_colour)  // Initialize theme radio group.
        theme_radio_group.setOnCheckedChangeListener {
                _, checkedId -> requireActivity().findViewById<RadioButton>(checkedId)?.apply {
            Toast.makeText(context, "Перезапустите приложение, чтобы изменения настроек темы вступили в силу.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun save_settings(){
        /** This method saves settings values to the preferences file. */

        rus_lang_btn = requireActivity().findViewById(R.id.language_btn_rus)
        eng_lang_btn = requireActivity().findViewById(R.id.language_btn_eng)

        male_voice_btn = requireActivity().findViewById(R.id.voice_btn_male)
        female_voice_btn = requireActivity().findViewById(R.id.voice_btn_female)

        lite_theme_btn = requireActivity().findViewById(R.id.theme_button_lite)
        dark_theme_btn = requireActivity().findViewById(R.id.theme_button_dark)

        val editor = prefs.edit()  // Get editor to edit the file.

        if (rus_lang_btn.isChecked){
            editor.putString("language", "rus").apply()  // If `rus lang` radio button checked - save `rus`.
        }

        if (eng_lang_btn.isChecked){
            editor.putString("language", "eng").apply()  // If `eng lang` radio button checked - save `eng`.
        }

        if (male_voice_btn.isChecked){
            editor.putString("voice", "m").apply()  // If `male voice` radio button checked - save `m`.
        }

        if (female_voice_btn.isChecked){
            editor.putString("voice", "f").apply()  // If `female voice` radio button checked - save `f`.
        }

        if (lite_theme_btn.isChecked){
            editor.putString("color_theme", "lite").apply()  // If `lite theme` radio button checked - save 'lite'.
        }

        if (dark_theme_btn.isChecked){
            editor.putString("color_theme", "dark").apply()  // If `dark theme` radio button checked - save `dark`.
        }
    }

    private fun load_settings(): Map<String, String?> {
        /** This method loads and returns settings from the preferences file. */

        rus_lang_btn = requireActivity().findViewById(R.id.language_btn_rus)
        eng_lang_btn = requireActivity().findViewById(R.id.language_btn_eng)

        male_voice_btn = requireActivity().findViewById(R.id.voice_btn_male)
        female_voice_btn = requireActivity().findViewById(R.id.voice_btn_female)

        lite_theme_btn = requireActivity().findViewById(R.id.theme_button_lite)
        dark_theme_btn = requireActivity().findViewById(R.id.theme_button_dark)

        val language: String? = prefs.getString("language", "rus")  // Get language, default value is russian.
        val voice: String? = prefs.getString("voice", "m")  // Get voice, default value is male.
        val color_theme: String? = prefs.getString("color_theme", "dark")  // Get theme, default value is dark.

        when (language){
            "rus" -> rus_lang_btn.isChecked = true  // If language rus - choose enable rus_lang_radiobtn.
            "eng" -> eng_lang_btn.isChecked = true  // If language eng - choose enable eng_lang_radiobtn.
        }

        when (voice){
            "m" -> male_voice_btn.isChecked = true  // If voice male - choose enable male_voice_radiobtn.
            "f" -> female_voice_btn.isChecked = true  // If voice female - choose enable female_voice_radiobth.
        }

        when (color_theme){
            "lite" -> lite_theme_btn.isChecked = true  // If lite theme - choose enable lite_theme_radiobtn.
            "dark" -> dark_theme_btn.isChecked = true  // If dark theme - choose enable dark_theme_radiobtn.
        }

        return mapOf("language" to language, "voice" to voice, "color_theme" to color_theme)  // Return values from settings file.
    }
}