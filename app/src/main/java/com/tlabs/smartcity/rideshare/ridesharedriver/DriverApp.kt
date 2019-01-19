package com.tlabs.smartcity.rideshare.ridesharedriver

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging
import com.mapbox.mapboxsdk.Mapbox

class DriverApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance(applicationContext, getString(R.string.mapbox))
        FirebaseMessaging.getInstance().subscribeToTopic("ride")
            .addOnCompleteListener { task ->
            }
    }
}