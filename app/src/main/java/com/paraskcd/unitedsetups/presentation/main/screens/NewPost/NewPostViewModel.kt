package com.paraskcd.unitedsetups.presentation.main.screens.NewPost

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.paraskcd.unitedsetups.core.interfaces.repository.IPostApiRepository
import com.paraskcd.unitedsetups.core.interfaces.repository.IUploadApiRepository
import com.paraskcd.unitedsetups.core.requests.posts.CreateNewPostRequest
import com.paraskcd.unitedsetups.core.requests.posts.PostMediaUrlRequest
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.domain.model.Upload
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewPostViewModel @Inject constructor(
    private val postApiRepository: IPostApiRepository,
    private val uploadApiRepository: IUploadApiRepository
) : ViewModel() {
    var selectedImages = mutableStateOf<List<Uri>>(emptyList())
    var postText = mutableStateOf("")
    var loading = mutableStateOf(false)

    suspend fun uploadMedia(): List<Upload> {
        val result = uploadApiRepository.uploadPostMedia(selectedImages.value)
        if (result.data != null) {
            Log.d("uploadMedia", "Media uploaded successfully: ${result.data}")
            return result.data!!
        } else {
            Log.d("uploadMedia", "Media upload failed: ${result.ex}")
            throw result.ex!!
        }
    }

    suspend fun createPost(): Post? {
        try {
            loading.value = true
            val uploadResult = uploadMedia()
            val postResult = postApiRepository.createNewPost(
                CreateNewPostRequest(
                    postText.value,
                    uploadResult.map { PostMediaUrlRequest(it.paths, it.thumbnails) }
                )
            )
            if (postResult.data != null) {
                loading.value = false
                return postResult.data!!
            } else {
                throw postResult.ex!!
            }
        } catch (e: Exception) {
            loading.value = false
            Log.d("createPost", "Post Create failed: ${e.localizedMessage}")
            return null;
        }
    }
}