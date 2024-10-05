package com.example.newsapp.API

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class APImanager {
    companion object{
       private var retrofit:Retrofit?=null
        private fun getinstance():Retrofit{
            if(retrofit==null){
                val logging: HttpLoggingInterceptor = HttpLoggingInterceptor(
                    object :HttpLoggingInterceptor.Logger {
                        override fun log(message: String) {
                            Log.e("api",message);
                        }
                    }
                )
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
                retrofit=Retrofit.Builder().client(client).baseUrl("https://newsapi.org/")
                    .addConverterFactory(GsonConverterFactory.create()).build()

            }
            return retrofit!!
        }
        fun getAPI():webserves{
            return getinstance().create(webserves::class.java)

        }
    }
}