package com.example.plantingapp.ui.screens.folders

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantingapp.R
import com.example.plantingapp.ui.components.containers.ItemsList
import com.example.plantingapp.ui.components.containers.TabView
import com.example.plantingapp.ui.screens.folders.card.AddFolderDialog
import com.example.plantingapp.ui.screens.folders.card.FolderCard
import com.example.plantingapp.ui.states.LoadingStates
import com.example.plantingapp.utils.Constants.DEBUG_TAG

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FoldersView(
    viewModel: FoldersViewModel
) {
    val folders = viewModel.folders.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()
    val showDialog =  remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    val refreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.getFolders() })
    val scope = rememberCoroutineScope()
    val firstItemVisible by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }
    TabView {
        Column {
            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.folders),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    fontWeight = FontWeight.Medium,
                )
                IconButton(
                    onClick = {
                        showDialog.value = !showDialog.value
                    },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "Add",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )
            }
            if(showDialog.value)
                AddFolderDialog(setShowDialog = {
                    showDialog.value = it
                })
            when (loadingState.value) {
                LoadingStates.Success -> {
                    ItemsList(
                        pullRefreshState, lazyListState,
                        refreshing, firstItemVisible, scope
                    ) {
                        items(folders.value.size) { index ->
                            val dismissState = rememberDismissState()

                            // check if the user swiped
                            if (dismissState.isDismissed(direction = DismissDirection.StartToEnd)) {
                                viewModel.delFolder(folders.value[index])
                                viewModel.getFolders()
                            }

                            SwipeToDismiss(
                                state = dismissState,
                                directions = setOf(DismissDirection.StartToEnd),
                                background = {
                                    // this background is visible when we swipe.
                                    // it contains the icon

                                    // background color
                                    val backgroundColor by animateColorAsState(
                                        when (dismissState.targetValue) {
                                            DismissValue.DismissedToEnd -> Color.Red.copy(alpha = 0.8f)
                                            else -> Color.Unspecified
                                        }, label = ""
                                    )

                                    // icon size
                                    val iconScale by animateFloatAsState(
                                        targetValue = if (dismissState.targetValue == DismissValue.DismissedToEnd) 1.3f else 0.5f,
                                        label = ""
                                    )

                                    Box(
                                        Modifier
                                            .fillMaxSize()
                                            .background(color = backgroundColor), // inner padding
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Icon(
                                            modifier = Modifier.scale(iconScale),
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = "Delete",
                                            tint = Color.White
                                        )
                                    }
                                },
                                dismissContent = {
                                    // list item
                                    FolderItem(folders.value[index], viewModel)
                                }
                            )
                        }
                    }
                }

                LoadingStates.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(R.string.folders_loading_error))
                        Button(onClick = { viewModel.getFolders() }) {
                            Text(stringResource(id = R.string.retry))
                        }
                    }
                }

                LoadingStates.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                }

                LoadingStates.NotLoading -> {
                    Text(stringResource(R.string.not_loading_yet))
                }
            }
        }
    }
}

