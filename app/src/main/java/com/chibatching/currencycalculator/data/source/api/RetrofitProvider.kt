package com.chibatching.currencycalculator.data.source.api

import com.chibatching.currencycalculator.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitProvider {

    val service: CurrencylayerService = Retrofit.Builder()
        .baseUrl("https://apilayer.net/api/")
        .addConverterFactory(Json.nonstrict.asConverterFactory(MediaType.get("application/json")))
        .client(
            OkHttpClient.Builder()
                .apply {
                    if (BuildConfig.DEBUG) {
                        addInterceptor(
                            HttpLoggingInterceptor().apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            }
                        )
                    }
                }
                .build()
        )
        .build()
        .create(CurrencylayerService::class.java)

    interface CurrencylayerService {
        @GET("list?access_key=${BuildConfig.ACCESS_KEY}")
        fun getSupportedCurrencyList(): Call<SupportedCurrencyListEntity>

        @GET("live?access_key=${BuildConfig.ACCESS_KEY}")
        fun getCurrencyLiveRateList(@Query("source") source: String): Call<CurrencyRateListEntity>
    }
}
