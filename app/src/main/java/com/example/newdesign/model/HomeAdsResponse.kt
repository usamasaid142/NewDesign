package com.example.newdesign.model

data class HomeAdsResponse(
    val Data: List<HomeAdsData>,
)

data class HomeAdsData(
    val IsVideo: Boolean,
    val VideoLink: String
)