package com.example.newdesign.notification.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NotificationResponse(
    @SerializedName("Data")
    val data: NotificationData?
): Serializable


data class NotificationData(
    @SerializedName("appCertificate")
    val appCertificate: Any?,
    @SerializedName("appID")
    val appID: Any?,
    @SerializedName("AppointmentId")
    val appointmentId: Int?,
    @SerializedName("channelName")
    val channelName: String?,
    @SerializedName("Token")
    val token: String?,
    @SerializedName("Token2")
    val token2: Any?
): Serializable