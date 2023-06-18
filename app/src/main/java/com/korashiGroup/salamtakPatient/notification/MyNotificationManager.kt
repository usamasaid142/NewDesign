package com.korashiGroup.salamtakPatient.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.korashiGroup.salamtakPatient.R
import java.util.*
import javax.inject.Inject


class MyNotificationManager @Inject constructor(private val mCtx: Application) {

    fun textNotification(title: String?, message: String?) {
        val rand = Random()
        val idNotification = rand.nextInt(1000000000)
        // deep link
        val args = Bundle()
        args.putString("callingType", title)
        val pendingDeeplink= NavDeepLinkBuilder(mCtx).setGraph(R.navigation.nav_graph).setDestination(R.id.videoCallFragment)
            .setArguments(args)
            .createPendingIntent()
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager =  mCtx.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "Channel_id_default", "Channel_name_default", NotificationManager.IMPORTANCE_HIGH
            )
            val attributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            notificationChannel.description = "Channel_description_default"
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.setSound(soundUri, attributes)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationBuilder = NotificationCompat.Builder(mCtx, "Channel_id_default")


        notificationBuilder.setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_notifications)
            .setTicker(mCtx.resources.getString(R.string.app_name))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(soundUri)
            .setContentIntent(pendingDeeplink)
            .setContentTitle(title)
            .setContentText(message)
        notificationManager.notify(idNotification, notificationBuilder.build())
    }

}