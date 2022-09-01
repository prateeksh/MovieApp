package com.company.movieapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.company.movieapp.api.ApiService
import com.company.movieapp.model.Media


class OnAirTvPagingSource (private val movieApi: ApiService,): PagingSource<Int, Media>(){


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        return try {
            val position = params.key ?: 1

            //generateRequests(position)
            val response = movieApi.getOnAirTv(position)

            val prevKey = if (position == 1) null else position - 1
            val nextKey = if (position == response!!.totalPages) null else position + 1

            LoadResult.Page(
                data = response.results,
                prevKey = prevKey,
                nextKey = nextKey
            )

        }catch (e: Exception){
            LoadResult.Error(e)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {

        //index of recent accessed page in anchorPosition
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}
