package com.example.imageseach.retrofit

import com.example.imageseach.data.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
       fun getApiService():NetWorkInterface{
           return getRetrofit().create(NetWorkInterface::class.java)
       }
}