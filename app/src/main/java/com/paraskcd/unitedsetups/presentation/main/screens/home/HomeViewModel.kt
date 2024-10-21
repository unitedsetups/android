package com.paraskcd.unitedsetups.presentation.main.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.core.common.TokenManager
import com.paraskcd.unitedsetups.core.interfaces.repository.IPostApiRepository
import com.paraskcd.unitedsetups.core.requests.posts.GetPostsRequest
import com.paraskcd.unitedsetups.domain.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
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
    var postIdLoading: MutableState<String?> = mutableStateOf(null)

    init {
        getLoggedInUserId()
    }

    fun startFetching() {
        stopFetching.value = false
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

    fun likePost(postId: String, isLiked: Boolean) {
        viewModelScope.launch {
            postIdLoading.value = postId
            var post: Post? = null
            if (isLiked) {
                val postResponse = postApiRepository.likePost(postId)
                post = postResponse.data
            } else {
                val postResponse = postApiRepository.dislikePost(postId)
                post = postResponse.data
            }

            if (post != null) {
                posts.value.find { it.id == postId }?.liked = post.liked
                posts.value.find { it.id == postId }?.disliked = post.disliked
                posts.value.find { it.id == postId }?.upvotes = post.upvotes
            }

            postIdLoading.value = null
        }
    }
}