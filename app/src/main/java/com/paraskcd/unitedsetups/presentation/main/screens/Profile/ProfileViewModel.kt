package com.paraskcd.unitedsetups.presentation.main.screens.Profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.core.interfaces.repository.IPostApiRepository
import com.paraskcd.unitedsetups.core.interfaces.repository.IUserApiRepository
import com.paraskcd.unitedsetups.core.requests.posts.GetPostsRequest
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userApiRepository: IUserApiRepository,
    private val postApiRepository: IPostApiRepository
) : ViewModel() {
    val user = mutableStateOf<User?>(null)
    val posts = mutableStateOf<List<Post>>(emptyList())
    val loading = mutableStateOf(false)

    suspend fun getUserById(userId: String) {
        val result = userApiRepository.getUserById(userId)
        user.value = result.data
    }

    suspend fun getMyProfile() {
        val result = userApiRepository.getMyProfile()
        user.value = result.data
    }

    suspend fun getPostsByUserId(userId: String?) {
        var id = userId
        loading.value = true
        if (id == null) {
            getMyProfile()
            id = user.value?.id
        }
        else {
            getUserById(userId)
        }
        val result = postApiRepository.getPosts(GetPostsRequest(null, 0, Constants.PAGE_SIZE, id))
        posts.value = result.data ?: emptyList()
        loading.value = false
    }
}