package com.korashiGroup.salamtakPatient.notification

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.korashiGroup.salamtakPatient.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {
    private  val TAG = "MyFirebaseMessagingServ"
    @Inject
    lateinit var mNotificationManager:MyNotificationManager
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("fcm", "Refreshed token: $token")
        DateUtils.setFcmToken(token)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e("fcm", "Refreshed token: ${message.notification?.link}")
        mNotificationManager.textNotification(message.notification?.title.toString(), message.notification?.title.toString())
       // (application as (SamlamtakApp)).ttrigerNotifiction(message.notification?.title.toString(), message.notification?.title.toString())

    }
    
}