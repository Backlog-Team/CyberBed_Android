package com.example.plantingapp.ui.screens.explore

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.plantingapp.R
import com.example.plantingapp.ui.components.cards.PlantCard
import com.example.plantingapp.ui.components.containers.TabView
import com.example.plantingapp.ui.screens.explore.custom.plants.CustomScreen
import com.example.plantingapp.ui.screens.explore.custom.plants.CustomViewModel
import com.example.plantingapp.ui.screens.explore.search.SearchField
import com.example.plantingapp.ui.screens.explore.search.SearchViewModel
import com.example.plantingapp.utils.Constants
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExploreView(
    viewModel: ExploreViewModel
) {
    val navigator = LocalNavigator.current
    val lazyListState = rememberLazyListState()
    val plants = viewModel.pagingData.value.collectAsLazyPagingItems()
    val searchViewModel: SearchViewModel = getViewModel()
    val customViewModel: CustomViewModel = getViewModel()

    val refreshing = viewModel.refreshing.value
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.refresh(plants) })
    val scope = rememberCoroutineScope()

    var showSearch by remember { mutableStateOf(false) }

    val firstItemVisible by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }
    TabView {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.explore),
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
                            showSearch = !showSearch
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    )
                    IconButton(
                        onClick = {
                            Log.i(Constants.DEBUG_TAG, "Add clicked")
                            navigator?.push(CustomScreen(customViewModel))
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    )
                }
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                if (showSearch) SearchField(viewModel = searchViewModel)
                Box(Modifier.pullRefresh(pullRefreshState)) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = lazyListState,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        plants.let {
                            if (it.loadState.refresh is LoadState.NotLoading) {
                                items(plants.itemCount) { index ->
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
                                        Text(stringResource(R.string.loading_error))
                                        Button(onClick = { it.retry() }) {
                                            Text(stringResource(R.string.retry))
                                        }
                                    }
                                }
                            } else if (it.loadState.refresh is LoadState.Loading) {
                                item {
                                    Box(modifier = Modifier.fillParentMaxSize()) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                }
                            } else if (it.loadState.refresh is LoadState.Error) {
                                item {
                                    Box(modifier = Modifier.fillParentMaxSize()) {
                                        Text(
                                            "Error",
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    PullRefreshIndicator(
                        refreshing,
                        pullRefreshState,
                        Modifier.align(Alignment.TopCenter)
                    )
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        AnimatedVisibility(
                            visible = !firstItemVisible,
                            enter = slideInVertically(initialOffsetY = { it / 2 }),
                            exit = slideOutVertically(targetOffsetY = { it / 2 })
                        ) {
                            IconButton(
                                modifier = Modifier.clip(CircleShape),
                                onClick = {
                                    scope.launch {
                                        lazyListState.animateScrollToItem(index = 0)
                                    }
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color.LightGray.copy(alpha = 0.7f)
                                ),
                                content = {
                                    Icon(
                                        painter = rememberVectorPainter(image = Icons.Default.ArrowUpward),
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
