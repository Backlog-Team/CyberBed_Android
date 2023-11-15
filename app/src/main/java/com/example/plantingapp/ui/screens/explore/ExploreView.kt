package com.example.plantingapp.ui.screens.explore

//import androidx.paging.compose.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.plantingapp.R
import com.example.plantingapp.ui.components.PlantCard
import com.example.plantingapp.ui.screens.explore.search.SearchScreen

@Composable
fun ExploreView(
    viewModel: ExploreViewModel
) {
    val navigator = LocalNavigator.current
    val lazyListState = rememberLazyListState()
    val plants = viewModel.pagingData.value.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Explore",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Row(
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        if (navigator != null) {
                            navigator.push(SearchScreen(viewModel))
                        }
                    },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            plants.let {
                items(plants.itemCount) {
                        index ->
                    if (plants[index] != null)
                        PlantCard(plants[index]!!)
                }
                if (it.loadState.append is LoadState.Loading) {
                    item {
                        CircularProgressIndicator()
                    }
                }
                if (it.loadState.append is LoadState.Error) {
                    item {
                        Text("LoadingError")
                    }
                }
            }
        }
    }
}
