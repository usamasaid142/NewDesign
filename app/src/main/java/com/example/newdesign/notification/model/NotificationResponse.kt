package com.example.newdesign.notification.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NotificationResponse(
    @SerializedName("AppointmentId")
    val appointmentId: Int?,
    @SerializedName("channelName")
    val channelName: String?,
    @SerializedName("Token")
    val token: String?
): Serializable