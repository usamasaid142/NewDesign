package com.korashiGroup.salamtakPatient.notification

import java.io.Serializable

data class DeepLinkItem(
    val id:Int?,
    val token:String?,
    val DoctorName:String?,
    val DoctorImage:String?,
    val Channelname:String?,
    val PatientID:Int?,
    val date:String?,
): Serializable
