package com.paraskcd.unitedsetups.domain.model

import java.time.LocalDateTime

data class Post(
    val id: String,
    val text: String,
    val createdDateTime: LocalDateTime,
    val updatedDateTime: LocalDateTime,
    val upvotes: Int,
    val clicks: Int,
    val deviceId: String,
    val postMediaUrls: List<PostMediaUrl>,
    val postedBy: PostedBy
)

data class PostedBy(
    val id: String,
    val name: String,
    val username: String,
    val profileImageThumbnailUrl: String
)

data class PostMediaUrl(
    val id: String,
    val path: String,
    val thumbnailPath: String
)
