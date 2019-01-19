package com.tlabs.smartcity.rideshare.ridesharedriver.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MsgService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom())
        Log.d(TAG, "Notification Message Body: " + (remoteMessage.notification?.body ?: "no body"))
    }

    companion object {
        private val TAG = "FCM Service"
    }
}