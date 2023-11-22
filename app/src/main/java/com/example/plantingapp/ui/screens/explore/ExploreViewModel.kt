package com.example.plantingapp.ui.screens.explore

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.models.Plant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val repository: PlantRepositoryInterface
): ViewModel() {
    private val _pagingData: MutableState<Flow<PagingData<Plant>>> = mutableStateOf(emptyFlow())
    val pagingData: State<Flow<PagingData<Plant>>> = _pagingData

    val refreshing = mutableStateOf(false)

    init {
        viewModelScope.launch {
            val response = repository.loadPlants()
            _pagingData.value = response.cachedIn(viewModelScope)
        }
    }

    fun refresh(items: LazyPagingItems<Plant>) {
        items.refresh()
    }
}