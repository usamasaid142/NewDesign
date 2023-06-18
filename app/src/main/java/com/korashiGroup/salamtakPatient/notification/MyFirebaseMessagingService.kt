package com.korashiGroup.salamtakPatient.notification

import android.util.Log
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

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e("TAG", "payload: ${message.notification?.link}")

        mNotificationManager.textNotification(message.notification?.title.toString(), message.notification?.body.toString())

    }



    
}