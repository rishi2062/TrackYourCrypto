package com.example.scogoass.Repository

import android.util.Log

import com.example.scogoass.Api.coinService
import com.example.scogoass.model.CoinDetail
import com.example.scogoass.model.Coins
import com.example.scogoass.model.ListCoins
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@ActivityScoped
class GetCoin @Inject constructor(private val api : coinService ) {
    suspend fun getCoinData() : ListCoins {
        val response = try {
            api.getCoinData()
        } catch (e: Exception) {
            Log.d("Error in Coin List", e.stackTrace.toString())
        }
//        val arr = response as ListCoins
//        Log.d("SuccessData" , "$arr")

        return response as ListCoins
    }

    suspend fun getCoinIdData(id : String) : CoinDetail {
        val response = try{
            api.getCoinIdData(id)
        }catch(e:Exception){
            Log.d("Error in Coin List" , e.stackTrace.toString())
        }
        return response as CoinDetail
    }
}