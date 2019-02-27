package com.example.loginpino.service

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val BASE_URL = "https://raw.githubusercontent.com/huyqv/assets/master/"
    private lateinit var mService: ApiTutorial

    private lateinit var instance: Retrofit


    fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return instance
    }

    fun getRetrofit(): ApiTutorial {
        return getInstance().create(ApiTutorial::class.java)
    }

}