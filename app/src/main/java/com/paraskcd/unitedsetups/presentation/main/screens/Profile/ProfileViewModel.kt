package com.paraskcd.unitedsetups.presentation.main.screens.Profile

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.core.common.DataOrException
import com.paraskcd.unitedsetups.core.common.TokenManager
import com.paraskcd.unitedsetups.core.interfaces.repository.IPostApiRepository
import com.paraskcd.unitedsetups.core.interfaces.repository.IUserApiRepository
import com.paraskcd.unitedsetups.core.requests.posts.GetPostsRequest
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userApiRepository: IUserApiRepository,
    private val postApiRepository: IPostApiRepository,
    private val tokenManager: TokenManager
) : ViewModel() {
    val user = mutableStateOf<User?>(null)
    val posts = mutableStateOf<List<Post>>(emptyList())
    val loading = mutableStateOf(false)
    var pageIndex = mutableStateOf(0)
    var stopFetching = mutableStateOf(false)
    var postIdLoading: MutableState<String?> = mutableStateOf(null)

    fun startFetching() {
        stopFetching.value = false
    }

    suspend fun getUserById(userId: String) {
        val result = userApiRepository.getUserById(userId)
        user.value = result.data
    }

    suspend fun getMyProfile() {
        val result = userApiRepository.getMyProfile()
        user.value = result.data
    }

    fun getPostsByUserId(userId: String?) {
        viewModelScope.launch(Dispatchers.Main) {
            pageIndex.value = 0
            loading.value = true
            var result: DataOrException<List<Post>, Exception>? = null
            if (userId.isNullOrBlank()) {
                result = postApiRepository.getPosts(GetPostsRequest(null, pageIndex.value, Constants.PAGE_SIZE, userId))
            } else {
                result = postApiRepository.getPosts(GetPostsRequest(null, pageIndex.value, Constants.PAGE_SIZE, user.value?.id))
            }
            posts.value = result.data ?: emptyList()
            loading.value = false
            if (result.data?.isNotEmpty() == true) {
                pageIndex.value = pageIndex.value + 1
            } else {
                stopFetching.value = true
            }
        }
    }

    fun fetchMorePostsByUserId() {
        viewModelScope.launch {
            loading.value = true
            val id = user.value?.id
            val result = postApiRepository.getPosts(GetPostsRequest(null, pageIndex.value, Constants.PAGE_SIZE, id))
            posts.value = posts.value.plus(result.data ?: emptyList())
            loading.value = false
            if (result.data?.isNotEmpty() == true) {
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

    fun share(postId: String): Intent {
        val post = posts.value.find { it.id == postId }
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TITLE, "United Setups")
            putExtra(Intent.EXTRA_TEXT, "${post?.postMediaUrls[0]?.path}")
            putExtra(Intent.EXTRA_TEXT, "User ${post?.postedBy?.username}  posted this Amazing Setup in United Setups, check it out.")
            type = "text/plain"
        }
        return Intent.createChooser(sendIntent, null)
    }
}