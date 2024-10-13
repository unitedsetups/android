package com.paraskcd.unitedsetups.core.entities.posts

import android.util.Log
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.domain.model.PostMediaUrl
import com.paraskcd.unitedsetups.domain.model.PostedBy
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class PostApiEntity(
    val id: String,
    val text: String,
    val createdDateTime: String?,
    val updatedDateTime: String?,
    val upvotes: Int,
    val clicks: Int,
    val deviceId: String?,
    val postMediaUrls: List<PostMediaUrlResponse>,
    val postedById: String,
    val postedBy: PostedByResponse,
)

data class PostMediaUrlResponse(
    val id: String,
    val path: String,
    val thumbnailPath: String
)

data class PostedByResponse(
    val name: String,
    val username: String,
    val profileImageThumbnailUrl: String
)

fun PostApiEntity.toPost(): Post {
    return Post(
        id,
        text,
        LocalDateTime.parse(createdDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")),
        LocalDateTime.parse(updatedDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")),
        upvotes,
        clicks,
        deviceId ?: "",
        postMediaUrls.map { it.toPostMediaUrl() },
        postedBy.toPostedBy(postedById)
    )
}

fun PostMediaUrlResponse.toPostMediaUrl(): PostMediaUrl {
    return PostMediaUrl(
        id,
        path,
        thumbnailPath
    )
}

fun PostedByResponse.toPostedBy(userId: String): PostedBy {
    return PostedBy(
        userId,
        name,
        username,
        profileImageThumbnailUrl
    )
}