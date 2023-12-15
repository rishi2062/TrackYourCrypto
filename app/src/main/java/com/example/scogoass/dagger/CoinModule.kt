package com.example.scogoass.dagger

import com.example.scogoass.Api.BASE_URL
import com.example.scogoass.Api.coinService
import com.example.scogoass.Repository.GetCoin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoinModule {
    @Singleton
    @Provides
    fun provideCoinRepository(
        service : coinService
    ) = GetCoin(service)

    @Singleton
    @Provides
    fun provideCoinApi() : coinService{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(coinService::class.java)
    }
}