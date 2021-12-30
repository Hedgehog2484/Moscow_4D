package com.android.moscow4D.fragments.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private var _username = MutableLiveData("Usernamme")
    val username: LiveData<String> = _username

    fun SaveUsername(newUsername: String){
        _username.value = newUsername
    }

}