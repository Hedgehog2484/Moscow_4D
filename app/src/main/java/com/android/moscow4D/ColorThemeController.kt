package com.android.moscow4D

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager


class ColorThemeController {
    companion object {
        private lateinit var sp: SharedPreferences
        fun setColorTheme(context: Context){
            sp = PreferenceManager.getDefaultSharedPreferences(context)
            val colorThemeListValue: String = sp.getString("color_theme", "3")!!
            Log.i("List Value", colorThemeListValue)
            if (colorThemeListValue == "1") AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            if (colorThemeListValue == "2") AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            if (colorThemeListValue == "3") AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}