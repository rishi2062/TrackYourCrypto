package com.example.scogoass.Api

import androidx.lifecycle.LiveData
import com.example.scogoass.model.CoinDetail
import com.example.scogoass.model.Coins
import com.example.scogoass.model.ListCoins
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


const val BASE_URL = "https://api.coinpaprika.com"
interface coinService {
    @GET("/v1/coins")
    suspend fun getCoinData() : ListCoins

    @GET("v1/coins/{id}")
    suspend fun getCoinIdData(
        @Path("id") id : String
    ) : CoinDetail
}

//object coinObject{
//    val coinInstance : coinService
//    init {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        coinInstance = retrofit.create(coinService::class.java)
//    }
//}