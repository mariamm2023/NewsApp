package com.example.newsapp.API

import com.example.newsapp.model.NewsResponse
import com.example.newsapp.model.SourceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface webserves {
    @GET("v2/top-headlines/sources")
    fun getsource(
      @Query("apiKey")apiKey:String
    ):Call<SourceResponse>
    @GET("v2/everything")
    fun getnews(
        @Query("apiKey")apikey:String,
        @Query("sources")sources:String
    ):Call<NewsResponse>
}