package com.example.scogoass

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.scogoass.ViewModel.CoinViewModel
import com.example.scogoass.model.CoinData
import com.example.scogoass.model.CoinDetail
import com.example.scogoass.model.Coins
//import com.google.accompanist.coil.CoilImage
import coil.compose.rememberImagePainter
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun CoinScreen(
    navController : NavController
){
    Surface(color = Color.LightGray, modifier = Modifier.fillMaxSize()) {
        Column{
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar(hint = "Search",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)){

            }
            Spacer(modifier = Modifier.height(16.dp))
            ShowList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier,
              hint : String = "",
              onSearch: (String) -> Unit = {}){
    var text by remember{
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint!="")
    }

    Box(modifier = modifier.padding(10.dp)){
        BasicTextField(value = text, onValueChange = {
            text = it
            onSearch(it)
//            Icon(
//                imageVector = Icons.Outlined.Email,
//                contentDescription = null
//            )
//
//            materialIcon(
//                "Search",
//                ImageVector = Icons.Rounded.Search,
//            )
        },
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 12.dp)

        )
        if(isHintDisplayed){
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                text = hint,
                color = Color.Black
            )
        }
    }

}


@Composable
fun ShowList(
    navController: NavController,
    viewModel: CoinViewModel = hiltViewModel()
){
    val coinList by remember {
        viewModel.coinList
    }
    val loadError by remember {
        viewModel.loadError
    }
    val isLoading by remember {
        viewModel.isLoading
    }

    LazyColumn(contentPadding = PaddingValues(16.dp)){
        val itemCount = if(coinList.size%2==0){
            coinList.size/2
        }
        else{
            coinList.size/2 + 1
        }
        items(itemCount){
            CoinRow(idx = it, entries = coinList, navController = navController)
        }
    }
}
@Composable
fun EachCoinDetail(
    entry : CoinData,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CoinViewModel = hiltViewModel()
){
    val defaultColor = MaterialTheme.colorScheme.surface
    var backColor by remember {
        mutableStateOf(defaultColor)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(backColor, defaultColor)
                )
            )
            .clickable {
                navController.navigate(
                    "CoinsDetailsActivity/${backColor.toArgb()}/${entry.id}"
                )
            }
    ){
        Column {
            Image(
                painter = rememberAsyncImagePainter(model = entry.image),
                contentDescription = entry.name,
                modifier = modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally))
//            ) {
////                CircularProgressIndicator(
////                    modifier = Modifier.scale(0.5f),
////                    color = MaterialTheme.colorScheme.primary
////                )
//            }
            Text(
                text = entry.image,
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CoinRow(
    idx : Int,
    entries : List<CoinData>,
    navController: NavController
){
    Column{
        Row{
            EachCoinDetail(entry = entries[idx*2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if(entries.size >= idx*2 + 2){
                EachCoinDetail(entry = entries[idx*2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            }
            else{
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

