package com.example.newdesign.model.booking

import java.io.Serializable

data class PatientAppointmentRequest(
    val DoctorId:Int?,
    val DoctorWorkingDayTimeId:Int?,
    val Fees:Int?,
    val AppointmentDate:String?,
    val Comment:String?,
    val IsBook:Boolean?,
): Serializable
