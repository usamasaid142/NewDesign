package com.example.newdesign.model.docotorsearch


import com.google.gson.annotations.SerializedName

data class DoctorsearchItemResponse(
    @SerializedName("Data")
    val `data`: Data?,
)

data class Data(
    @SerializedName("Items")
    val items: List<Item?>?,
    @SerializedName("TotalCount")
    val totalCount: Int?
)

data class HealthEntity(
    @SerializedName("Address")
    val address: String?,
    @SerializedName("ApartmentNo")
    val apartmentNo: Any?,
    @SerializedName("AreaName")
    val areaName: String?,
    @SerializedName("BlockNo")
    val blockNo: Any?,
    @SerializedName("CityName")
    val cityName: String?,
    @SerializedName("ClinicId")
    val clinicId: Int?,
    @SerializedName("ClinicLatitude")
    val clinicLatitude: String?,
    @SerializedName("ClinicLongitude")
    val clinicLongitude: String?,
    @SerializedName("Email")
    val email: String?,
    @SerializedName("FixedFee")
    val fixedFee: Int?,
    @SerializedName("FloorNo")
    val floorNo: Int?,
    @SerializedName("Logo")
    val logo: String?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Phone")
    val phone: String?
)

data class Item(
    @SerializedName("AvailableFrom")
    val availableFrom: Any?,
    @SerializedName("ClinicId")
    val clinicId: Int?,
    @SerializedName("ClinicName")
    val clinicName: Any?,
    @SerializedName("DoctorId")
    val doctorId: Int?,
    @SerializedName("DoctorName")
    val doctorName: String?,
    @SerializedName("FeesFrom")
    val feesFrom: Int?,
    @SerializedName("FeesTo")
    val feesTo: Int?,
    @SerializedName("HealthEntities")
    val healthEntities: List<HealthEntity?>?,
    @SerializedName("Image")
    val image: String?,
    @SerializedName("MedicalExamationTypes")
    val medicalExamationTypes: Any?,
    @SerializedName("NumVisites")
    val numVisites: Int?,
    @SerializedName("Rate")
    val rate: Int?,
    @SerializedName("SeniortyLevelName")
    val seniortyLevelName: String?,
    @SerializedName("SpecialistName")
    val specialistName: String?,
    @SerializedName("SubSpecialistName")
    val subSpecialistName: List<String?>?,
    @SerializedName("SumRate")
    val sumRate: Int?,
    @SerializedName("WaitingTime")
    val waitingTime: Int?
)