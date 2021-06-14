package com.example.weather.data

import android.icu.text.DateFormat.getDateTimeInstance
import com.google.android.material.timepicker.TimeFormat
import java.text.DateFormat
import java.text.DateFormat.getDateTimeInstance
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
){
    val sunriseformat:String
        get() =  SimpleDateFormat("HH:mm",Locale.ENGLISH).format(sunrise*1000)



    val sunsetformat:String
        get() = SimpleDateFormat("HH:mm").format(sunset*1000)

}