package com.paraskcd.unitedsetups.presentation.main.screens.Profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paraskcd.unitedsetups.core.common.Constants
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
    private val postApiRepository: IPostApiRepository
) : ViewModel() {
    val user = mutableStateOf<User?>(null)
    val posts = mutableStateOf<List<Post>>(emptyList())
    val loading = mutableStateOf(false)
    var pageIndex = mutableStateOf(0)
    var stopFetching = mutableStateOf(false)


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
            val result = postApiRepository.getPosts(GetPostsRequest(null, pageIndex.value, Constants.PAGE_SIZE, userId))
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
            posts.value = result.data ?: emptyList()
            loading.value = false
            if (result.data?.isNotEmpty() == true) {
                pageIndex.value = pageIndex.value + 1
            } else {
                stopFetching.value = true
            }
        }
    }
}