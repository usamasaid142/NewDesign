package com.example.newdesign


import android.app.Application
import com.example.newdesign.utils.DateUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SamlamtakApp: Application() {
  override fun onCreate() {
    super.onCreate()
    DateUtils.setup(applicationContext)
  }
  }