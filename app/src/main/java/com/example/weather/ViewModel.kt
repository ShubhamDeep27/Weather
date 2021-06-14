package com.example.weather

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.Weather

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response


class ViewModel @ViewModelInject constructor(private val repository: Repository):ViewModel(){

    var data:MutableLiveData<Response<Weather>> = MutableLiveData()


    /*private val taskeventchannel= Channel<Event>()
    val taskevent=taskeventchannel.receiveAsFlow()*/

    fun getweather(city:String)=viewModelScope.launch {

        val response=repository.getweather(city)
        if (response.isSuccessful)
        {
            data.value=response
            Log.d("pig",response.toString())
        }

    }
    fun getweatherusinglocation(lat:Double,lon:Double)=viewModelScope.launch {

        val response=repository.getweatherlocation(lat,lon)
        if (response.isSuccessful)
        {
            data.value=response
            Log.d("pig",response.toString())
        }

    }













}