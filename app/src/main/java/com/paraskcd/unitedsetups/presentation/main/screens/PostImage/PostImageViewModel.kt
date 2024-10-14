package com.paraskcd.unitedsetups.presentation.main.screens.PostImage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.paraskcd.unitedsetups.core.interfaces.repository.IPostApiRepository
import com.paraskcd.unitedsetups.domain.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostImageViewModel @Inject constructor(private val postApiRepository: IPostApiRepository) : ViewModel() {
    val post = mutableStateOf<Post?>(null)
    val loading = mutableStateOf(false)

    suspend fun getPostById(postId: String) {
        loading.value = true
        val result = postApiRepository.getPostById(postId)
        if (result.data != null) {
            post.value = result.data
            loading.value = false
        }
    }
}