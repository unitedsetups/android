package com.paraskcd.unitedsetups.presentation.main.screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.core.common.TokenManager
import com.paraskcd.unitedsetups.core.interfaces.repository.IPostApiRepository
import com.paraskcd.unitedsetups.core.requests.posts.GetPostsRequest
import com.paraskcd.unitedsetups.domain.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postApiRepository: IPostApiRepository,
    private val tokenManager: TokenManager
) : ViewModel() {
    var posts = mutableStateOf<List<Post>>(emptyList())
    var loading = mutableStateOf(false)
    var loggedInUserId = mutableStateOf("")
    var scrollIndex = mutableStateOf(0)
    var scrollOffset = mutableStateOf(0)
    var pageIndex = mutableStateOf(0)
    var stopFetching = mutableStateOf(false)

    init {
        getLoggedInUserId()
        fetchPosts()
    }

     fun fetchPosts() {
        viewModelScope.launch {
            pageIndex.value = 0
            loading.value = true
            val postResponse = postApiRepository.getPosts(GetPostsRequest(null, pageIndex.value, Constants.PAGE_SIZE, null))
            posts.value = postResponse.data ?: emptyList()
            loading.value = false
            if (postResponse.data?.isNotEmpty() == true) {
                pageIndex.value = pageIndex.value + 1
            }
        }
    }

    fun getLoggedInUserId() {
       loggedInUserId.value = tokenManager.getUserId().toString()
    }

    fun fetchMorePosts() {
        viewModelScope.launch {
            loading.value = true
            val postResponse = postApiRepository.getPosts(GetPostsRequest(null, pageIndex.value, Constants.PAGE_SIZE, null))
            posts.value = posts.value.plus(postResponse.data ?: emptyList())
            loading.value = false
            if (postResponse.data?.isNotEmpty() == true) {
                pageIndex.value = pageIndex.value + 1
            } else {
                stopFetching.value = true
            }
        }
    }
}