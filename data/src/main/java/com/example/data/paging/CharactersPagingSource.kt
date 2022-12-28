package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.api.Api
import com.example.data.extensions.characterModelToCharacters
import com.example.domain.model.Character

class CharactersPagingSource(private val api: Api, private val name: String?) :
    PagingSource<Int, Character>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val position = params.key ?: 1
            val response = name?.let {
                api.getCharactersByName(name)
            } ?: api.getCharacters(position)

            return LoadResult.Page(
                data = response.characterModelToCharacters().character,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.info.pages) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}