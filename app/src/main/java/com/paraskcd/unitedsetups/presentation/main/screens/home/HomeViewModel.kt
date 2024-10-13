package com.paraskcd.unitedsetups.presentation.main.screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.core.interfaces.repository.IPostApiRepository
import com.paraskcd.unitedsetups.core.requests.posts.GetPostsRequest
import com.paraskcd.unitedsetups.domain.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postApiRepository: IPostApiRepository
) : ViewModel() {
    private var _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>>
        get() = _posts
    var loading = mutableStateOf(false)

    suspend fun fetchPosts() {
        loading.value = true
        val postResponse = postApiRepository.getPosts(GetPostsRequest(null, 0, Constants.PAGE_SIZE))
        _posts.value = postResponse.data ?: emptyList()
        loading.value = false
    }
}