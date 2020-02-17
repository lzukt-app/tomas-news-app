package com.example.tomasNewsApp.source

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tomasNewsApp.NewsApplication
import com.example.tomasNewsApp.utils.database.NewsDatabase
import com.example.tomasNewsApp.utils.location.LocationManager
import com.google.android.gms.location.LocationServices

class SourceViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(
        application
    ) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val manager = LocationManager(
            LocationServices.getFusedLocationProviderClient(application),
            LocationServices.getSettingsClient(application)
        )

        val service = (application as NewsApplication).retrofit.create(SourceService::class.java)

        return SourceViewModel(
            service,
            NewsDatabase.getInstance(application).sourceDao,
            manager
        ).apply { onCreate() } as T
    }
}