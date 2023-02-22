package com.example.newdesign.model.booking

import android.os.Parcelable


data class AppointmentBooking(
    val time:List<String>?,
    val timeInterval:List<Int>?,
    val DoctorWorkingDayTimeId:List<Int>?
):java.io.Serializable
