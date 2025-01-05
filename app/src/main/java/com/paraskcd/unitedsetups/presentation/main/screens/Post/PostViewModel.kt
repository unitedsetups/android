package com.paraskcd.unitedsetups.presentation.main.screens.Post

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paraskcd.unitedsetups.core.common.TokenManager
import com.paraskcd.unitedsetups.core.interfaces.repository.IPostApiRepository
import com.paraskcd.unitedsetups.core.interfaces.repository.IPostThreadApiRepository
import com.paraskcd.unitedsetups.core.interfaces.repository.IUploadApiRepository
import com.paraskcd.unitedsetups.core.requests.posts.PostMediaUrlRequest
import com.paraskcd.unitedsetups.core.requests.postthreads.CreateNewPostThreadRequest
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.domain.model.PostThread
import com.paraskcd.unitedsetups.domain.model.Upload
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postApiRepository: IPostApiRepository,
    private val postThreadApiRepository: IPostThreadApiRepository,
    private val uploadApiRepository: IUploadApiRepository,
    private val tokenManager: TokenManager
) : ViewModel() {
    val post = mutableStateOf<Post?>(null)
    val loading = mutableStateOf(false)
    val loggedInUserId: MutableState<String?> = mutableStateOf(null)
    val postIdLoading: MutableState<String?> = mutableStateOf(null)
    var selectedImages = mutableStateOf<List<Uri>>(emptyList())
    val postThreadIdLoading: MutableState<String?> = mutableStateOf(null)
    var text by mutableStateOf("")
        private set
    val replyParentPostThread: MutableState<PostThread?> = mutableStateOf(null)

    fun updateText(input: String) {
        text = input
    }

    fun getLoggedInUserId() {
        loggedInUserId.value = tokenManager.getUserId().toString()
    }

    fun getPostById(postId: String) {
        viewModelScope.launch {
            loading.value = true
            val result = postApiRepository.getPostById(postId)
            result.data?.let { data ->
                Log.d("getPostById", data.toString())
                post.value = data
            }
            loading.value = false
        }
    }

    fun likePost(isLiked: Boolean) {
        viewModelScope.launch {
            post.value?.id?.let { postId ->
                postIdLoading.value = postId
                if (isLiked) {
                    val postResponse = postApiRepository.likePost(postId)
                    post.value = postResponse.data
                } else {
                    val postResponse = postApiRepository.dislikePost(postId)
                    post.value = postResponse.data
                }
                postIdLoading.value = null
            }
        }
    }

    fun likePostThread(isLiked: Boolean, postThreadId: String) {
        viewModelScope.launch {
            postThreadIdLoading.value = postThreadId
            var postThread: PostThread? = null
            if (isLiked) {
                val postResponse = postThreadApiRepository.likePostThread(postThreadId)
                postThread = postResponse.data
            } else {
                val postResponse = postThreadApiRepository.dislikePostThread(postThreadId)
                postThread = postResponse.data
            }

            postThread?.let { postThread ->
                getPostById(post.value!!.id)
            }

            postThreadIdLoading.value = null
        }
    }

    suspend fun uploadMedia(): List<Upload> {
        if (selectedImages.value.isEmpty()) {
            return emptyList()
        }
        val result = uploadApiRepository.uploadPostMedia(selectedImages.value)
        result.data?.let { data ->
            return data
        } ?: {
            result.ex?.let { ex ->
                throw ex
            }
        }
        return emptyList()
    }

    suspend fun createPostThread() {
        try {
            loading.value = true
            val uploadResult = uploadMedia()
            post.value?.let {
                val postResult = postThreadApiRepository.createNewPostThread(
                    CreateNewPostThreadRequest(
                        it.id,
                        replyParentPostThread.value?.id,
                        text,
                        uploadResult.map { PostMediaUrlRequest(it.paths, it.thumbnails) }
                    )
                )
                postResult.data?.let { data ->
                    getPostById(post.value!!.id)
                    text = ""
                    selectedImages.value = emptyList()
                    replyParentPostThread.value = null
                } ?: {
                    postResult.ex?.let { ex ->
                        throw ex
                    }
                }
            }
        } catch (e: Exception) {
            loading.value = false
            Log.d("createPost", "Post Create failed: ${e.localizedMessage}")
        }
    }

    fun share(): Intent {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TITLE, "United Setups")
            putExtra(Intent.EXTRA_TEXT, "${post.value?.postMediaUrls[0]?.path}")
            putExtra(Intent.EXTRA_TEXT, "User ${post.value?.postedBy?.username}  posted this Amazing Setup in United Setups, check it out.")
            type = "text/plain"
        }
        return Intent.createChooser(sendIntent, null)
    }

    fun getReplyPostThread(postThread: PostThread) {
        replyParentPostThread.value = postThread
    }

    fun deletePost() {
        viewModelScope.launch {
            post.value?.id?.let { postId ->
                postApiRepository.deletePost(postId)
            }
        }
    }
}