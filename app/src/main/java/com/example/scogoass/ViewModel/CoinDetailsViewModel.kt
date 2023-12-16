package com.example.scogoass.ViewModel

import androidx.lifecycle.ViewModel
import com.example.scogoass.Repository.GetCoin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val repo: GetCoin
) : ViewModel() {

}