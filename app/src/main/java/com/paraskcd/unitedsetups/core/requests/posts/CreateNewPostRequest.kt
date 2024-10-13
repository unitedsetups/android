package com.paraskcd.unitedsetups.core.requests.posts

data class CreateNewPostRequest(
    val text: String,
    val postMediaUrls: List<PostMediaUrlRequest>
)

data class PostMediaUrlRequest(
    val path: String,
    val thumbnailPath: String
)