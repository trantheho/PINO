package com.example.loginpino.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiTutorial {

    fun downloadFile(@Url url : String) : Call<ResponseBody>

}