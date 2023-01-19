package com.example.newdesign.model

data class HomeAdsResponse(
    val Data: List<HomeAdsData>,
    val Message: String,
    val MessageCode: Int,
    val Success: Boolean
)

data class HomeAdsData(
    val IsVideo: Boolean,
    val VideoLink: String
)