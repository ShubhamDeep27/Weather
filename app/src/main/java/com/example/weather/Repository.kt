package com.example.weather

import com.example.weather.api.ApiServices
import com.example.weather.data.Weather

import com.example.weather.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject


class Repository @Inject constructor(private val api:ApiServices) {

    suspend fun getweather(city: String): Response<Weather> = api.getweather(city, API_KEY)
    suspend fun getweatherlocation(lat: Double, lon: Double): Response<Weather> =
        api.getweatherlocation(lat, lon, API_KEY)
}