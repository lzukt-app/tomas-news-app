package com.example.tomasNewsApp.utils.location

import android.app.Activity
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import io.reactivex.Maybe
import io.reactivex.Observable

class LocationManager(
    private val locationClient: FusedLocationProviderClient,
    private val settingsClient: SettingsClient
) {
    val locationUpdates: Observable<Location>
        get() = Observable.create { emitter ->
            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    emitter.onNext(result.lastLocation)
                }
            }
            locationClient.requestLocationUpdates(
                LOCATION_REQUEST,
                callback,
                Looper.getMainLooper()
            )
            emitter.setCancellable { locationClient.removeLocationUpdates(callback) }
        }

    val lastLocation: Maybe<Location>
        get() = Maybe.create { emitter ->
            locationClient.lastLocation.addOnCompleteListener {
                if (it.result != null) emitter.onSuccess(it.result!!)
                emitter.onComplete()
            }
        }

    fun checkLocationSettings(activity: Activity) {
        settingsClient.checkLocationSettings(SETTINGS_REQUEST)
            .addOnCompleteListener { checkLocationSettingsResponse(activity, it) }

    }

    private fun checkLocationSettingsResponse(
        activity: Activity,
        task: Task<LocationSettingsResponse>
    ) {
        try {
            task.getResult(ApiException::class.java)
        } catch (exception: ApiException) {
            if (exception.statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                tryStartResolution(activity, exception)
            }
        }
    }

    private fun tryStartResolution(
        activity: Activity,
        exception: ApiException
    ) {
        try {
            val resolvable = exception as ResolvableApiException
            resolvable.startResolutionForResult(activity, 22)
        } catch (cause: IntentSender.SendIntentException) {
            // Do nothing
        } catch (cause: ClassCastException) {
            // Do nothing
        }
    }

    companion object {
        private const val UPDATE_INTERVAL_IN_SECONDS: Long = 5
        private const val MILLISECONDS_PER_SECOND: Long = 1000
        const val UPDATE_INTERVAL: Long =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS
        private const val FASTEST_INTERVAL_IN_SECONDS: Long = 1
        const val FASTEST_INTERVAL: Long =
            MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS

        val LOCATION_REQUEST: LocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL)
        var SETTINGS_REQUEST: LocationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(LOCATION_REQUEST)
            .setAlwaysShow(true)
            .build()
    }

}