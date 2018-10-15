package com.edoubletech.newsfeed.ui

import com.edoubletech.newsfeed.data.ResourceState
import com.edoubletech.newsfeed.data.model.News

sealed class NewsState(val resourceState: ResourceState,
                       val data: List<News>? = null,
                       val errorMessage: String? = null) {

    data class Success(private val newsList: List<News>) : NewsState(ResourceState.SUCCESS,
            newsList)

    data class Error(private val message: String? = null) : NewsState(ResourceState.SUCCESS,
            errorMessage = message)

    object Loading : NewsState(ResourceState.LOADING)
}