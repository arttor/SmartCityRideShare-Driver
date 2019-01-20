package com.tlabs.smartcity.rideshare.ridesharedriver.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tlabs.smartcity.rideshare.ridesharedriver.data.RegDevReq
import com.tlabs.smartcity.rideshare.ridesharedriver.data.RegDriverReq
import kotlinx.coroutines.Deferred
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface BackendApi {
    @POST("/registerDevice")
    fun registerDevice(@Body body: RegDevReq): Deferred<ResponseBody>

    @POST("/registerDriver")
    fun registerDriver(@Body body: RegDriverReq): Deferred<ResponseBody>

    companion object {
        val instance = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .baseUrl("http://10.177.1.146:8000")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(BackendApi::class.java)
    }
}
