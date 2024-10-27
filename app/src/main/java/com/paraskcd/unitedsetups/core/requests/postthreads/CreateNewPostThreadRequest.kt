package com.paraskcd.unitedsetups.core.requests.postthreads

import com.paraskcd.unitedsetups.core.requests.posts.PostMediaUrlRequest

data class CreateNewPostThreadRequest(
    val postId: String,
    val parentPostThreadId: String?,
    val text: String,
    val postMediaUrls: List<PostMediaUrlRequest>
)
