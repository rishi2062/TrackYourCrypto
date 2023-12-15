package com.example.scogoass

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.scogoass.model.Coins
import com.example.scogoass.ui.theme.ScogoAssTheme
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "CoinsActivity"){
                composable("CoinsActivity"){
                    CoinScreen(navController = navController)
                }
                composable("CoinsDetailsActivity/{dominantColor}/{coinId}",
                    arguments = listOf(
                        navArgument("dominantColor"){
                            type = NavType.IntType
                },
                        navArgument("coinDetails"){
                            type = NavType.StringType
                        })){
                    val dominantColor = remember{
                        val color = it.arguments?.getInt("dominantColor")
                        color?.let{ Color(it) }?: Color.Gray
                    }
                    val coinDetails = remember{
                        it.arguments?.getString("coinId")
                    }
                }
            }
        }
    }
}


//private fun getCoins() {
//    val coins = coinObject.coinInstance.getCoinData()
//    coins.enqueue(object : Callback<List<Coins>> {
//        override fun onResponse(
//            call: Call<List<Coins>>,
//            response: Response<List<Coins>>
//        ) {
//            val coins = response.body()
//            //Log.d("SystemCoins",${coins.toString()})
//            if (coins != null) {
//                Log.d("SystemCoins", coins.toList().toString())
//                //coinList = coins.toMutableList()
//            }
//        }
//
//        override fun onFailure(call: Call<List<Coins>>, t: Throwable) {
//            Log.d("SystemError", "Error", t)
//        }
//    })
//}