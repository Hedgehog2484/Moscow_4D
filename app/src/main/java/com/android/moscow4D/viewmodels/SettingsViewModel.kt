package com.android.moscow4D.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private var _username = MutableLiveData("Username")
    val username: LiveData<String> = _username

    fun saveUsername(newUsername: String){
        _username.value = newUsername
    }
}