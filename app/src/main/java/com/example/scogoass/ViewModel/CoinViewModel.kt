package com.example.scogoass.ViewModel

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.scogoass.Repository.GetCoin
import com.example.scogoass.handler.ResultHandler
import com.example.scogoass.model.CoinData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CoinViewModel @Inject constructor(
    private val repo : GetCoin
) : ViewModel() {
    private var currPage = 0
    val coinList = mutableStateOf<List<CoinData>>(listOf())

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    private var searchedList = listOf<CoinData>()
    private var isStartingSearch = true
    var isSearching = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadCoinDetail()
    }

    fun searchList(query: String) {
        val listToSearch = if (isStartingSearch) {
            coinList.value
        } else {
            searchedList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                coinList.value = searchedList
                isSearching.value = false
                isStartingSearch = true
                return@launch
            } else {
                val results = listToSearch.filter {
                    it.name.contains(query.trim(), ignoreCase = true)
                }
                if (isStartingSearch) {
                    searchedList = coinList.value
                    isStartingSearch = false
                }
                coinList.value = results
                isSearching.value = true
            }
        }
    }

    fun loadCoinDetail() {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repo.getCoinData(currPage, 100)) {
                is ResultHandler.Success -> {
                    endReached.value = currPage * 100 >= result.data!!.size
                    val coinEntries = result.data.mapIndexed { index, coins ->
                        val url = "https://static.coinpaprika.com/coin/${coins.id}/logo.png"
                        CoinData(coins.name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                java.util.Locale.ROOT
                            ) else it.toString()
                        }, url, coins.id)
                    }
                    currPage++
                    loadError.value = ""
                    isLoading.value = false
                    coinList.value = coinEntries
                }

                is ResultHandler.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

            }
        }
    }
    fun calcBackGroundColor(drawable: Drawable, onFinish : (Color) -> Unit){
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888,true)
        Palette.from(bmp).generate{
            it?.dominantSwatch?.rgb?.let{colorVal ->
                onFinish(Color(colorVal))
            }
        }
    }
}

