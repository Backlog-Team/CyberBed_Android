package com.example.plantingapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.plantingapp.data.remote.PlantApi
import com.example.plantingapp.domain.models.Plant

class PlantPagingSource(
    private val api: PlantApi,
): PagingSource<Int, Plant>() {
    override fun getRefreshKey(state: PagingState<Int, Plant>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Plant> {
        return try {
            val page = params.key ?: 1
            val response = api.searchPlants(page = page)

            LoadResult.Page(
                data = response.body() ?: listOf(),
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.body()?.isEmpty() == true) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}