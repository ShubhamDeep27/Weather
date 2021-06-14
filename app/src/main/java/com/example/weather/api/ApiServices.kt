package com.example.weather.api

import com.example.weather.data.Weather

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {


    @GET("weather")
    suspend fun getweather(
        @Query("q")q:String,
        @Query("appid")appid:String
    ):Response<Weather>


    @GET("weather")
    suspend fun getweatherlocation(
        @Query("lat")lat:Double,
        @Query("lon")lon:Double,
        @Query("appid")appid:String
    ):Response<Weather>



}