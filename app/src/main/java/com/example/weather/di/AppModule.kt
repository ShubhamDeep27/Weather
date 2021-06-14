package com.example.weather.di

import com.example.weather.api.ApiServices
import com.example.weather.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun provideretofitInstance():Retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideweatherapi(retrofit: Retrofit): ApiServices =
        retrofit.create(ApiServices::class.java)




}