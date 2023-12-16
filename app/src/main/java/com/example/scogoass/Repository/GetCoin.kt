package com.example.scogoass.Repository

import android.util.Log
import com.example.scogoass.Api.coinService
import com.example.scogoass.handler.ResultHandler
import com.example.scogoass.model.CoinDetail
import com.example.scogoass.model.ListCoins
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.delay
import javax.inject.Inject

@ActivityScoped
class GetCoin @Inject constructor(private val api : coinService ) {
    suspend fun getCoinData(page: Int, pageSize: Int): ResultHandler<ListCoins> {
        delay(100L)
        val startingIndex = page * pageSize

        val result = try {
            api.getCoinData()
        } catch (e: Exception) {
            Log.d("Error in Coin List", e.message.toString())
            return ResultHandler.Error("Error in fetching request....", null)
        }
        if (startingIndex + pageSize <= result.size) {
            result.slice(startingIndex until startingIndex + pageSize)
            return ResultHandler.Success(result)
        }
        return ResultHandler.Success(result.subList(0, 0) as ListCoins)
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