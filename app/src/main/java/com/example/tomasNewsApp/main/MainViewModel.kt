package com.example.tomasNewsApp.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tomasNewsApp.utils.SingleLiveData

class MainViewModel constructor(
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _showTutorial = SingleLiveData<Unit>()
    val showTutorial: LiveData<Unit> get() = _showTutorial

    fun onCreate() {
        if (!preferences.getBoolean("tutorial_is_shown", false)) {
            _showTutorial.postValue(Unit)
            preferences.edit().putBoolean("tutorial_is_shown", true).apply()
        }
    }
}