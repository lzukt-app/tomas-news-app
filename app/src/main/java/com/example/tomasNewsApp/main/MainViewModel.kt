package com.example.tomasNewsApp.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tomasNewsApp.utils.SingleLiveData

class MainViewModel constructor(
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _showTutorial = SingleLiveData<Boolean>()
    val showTutorial: LiveData<Boolean> get() = _showTutorial

    fun onCreate() {
        _showTutorial.postValue(!preferences.getBoolean("tutorial_is_shown", false))
        preferences.edit().putBoolean("tutorial_is_shown", true).apply()
    }
}