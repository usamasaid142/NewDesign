package com.example.newdesign.model.booking


import com.google.gson.annotations.SerializedName

data class CreatePatientAppointmentResponse(
    @SerializedName("AppointmentDate")
    val appointmentDate: String?,
    @SerializedName("doctorDto")
    val doctorDto: DoctorDto?,
    @SerializedName("DoctorName")
    val doctorName: String?,
    @SerializedName("DoctorWorkingDayTimeId")
    val doctorWorkingDayTimeId: Int?,
    @SerializedName("DurationMedicalExaminationId")
    val durationMedicalExaminationId: Int?,
    @SerializedName("Fees")
    val fees: Int?,
    @SerializedName("HealthEntityPhoneDtos")
    val healthEntityPhoneDtos: List<String?>?,
    @SerializedName("HealthentityAddress")
    val healthentityAddress: String?,
    @SerializedName("HealthentityName")
    val healthentityName: String?,
    @SerializedName("IsBook")
    val isBook: Boolean?,
    @SerializedName("MaxNoOfPatients")
    val maxNoOfPatients: Int?,
    @SerializedName("MedicalExaminationTypeName")
    val medicalExaminationTypeName: String?,
    @SerializedName("PatientName")
    val patientName: String?,
    @SerializedName("PatientNumber")
    val patientNumber: String?,
    @SerializedName("TimeInterval")
    val timeInterval: Int?
)

data class DoctorDto(
    @SerializedName("Birthday")
    val birthday: String?,
    @SerializedName("CreatedByName")
    val createdByName: String?,
    @SerializedName("Email")
    val email: String?,
    @SerializedName("FullName")
    val fullName: String?,
    @SerializedName("FullNameAr")
    val fullNameAr: String?,
    @SerializedName("GenderName")
    val genderName: String?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Image")
    val image: String?,
    @SerializedName("LastNotification")
    val lastNotification: String?,
    @SerializedName("Phone")
    val phone: String?,
    @SerializedName("SeniorityLevelName")
    val seniorityLevelName: String?,
    @SerializedName("SpecialistName")
    val specialistName: String?,
    @SerializedName("StatusId")
    val statusId: Int?,
    @SerializedName("StatusName")
    val statusName: String?,
    @SerializedName("UserId")
    val userId: Int?
)