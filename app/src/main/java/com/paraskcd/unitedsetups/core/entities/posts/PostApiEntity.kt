package com.paraskcd.unitedsetups.core.entities.posts

import com.paraskcd.unitedsetups.core.entities.postthreads.PostThreadApiEntity
import com.paraskcd.unitedsetups.core.entities.postthreads.toPostThread
import com.paraskcd.unitedsetups.domain.model.Post
import com.paraskcd.unitedsetups.domain.model.PostMediaUrl
import com.paraskcd.unitedsetups.domain.model.PostedBy
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

data class PostApiEntity(
    val id: String,
    val text: String,
    val createdDateTime: String?,
    val updatedDateTime: String?,
    val upvotes: Int,
    val clicks: Int,
    val deviceId: String?,
    val postMediaUrls: List<PostMediaUrlResponse>,
    val postThreads: List<PostThreadApiEntity>,
    val postedById: String,
    val postedBy: PostedByResponse,
    val liked: Boolean,
    val disliked: Boolean
)

data class PostMediaUrlResponse(
    val id: String,
    val path: String,
    val thumbnailPath: String
)

data class PostedByResponse(
    val name: String,
    val username: String,
    val profileImageThumbnailUrl: String?
)

fun PostApiEntity.toPost(): Post {
    return Post(
        id,
        text,
        LocalDateTime.parse(
            createdDateTime,
            DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss.S'Z'")
                .withResolverStyle(ResolverStyle.LENIENT)
        ),
        LocalDateTime.parse(
            updatedDateTime,
            DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss.S'Z'")
                .withResolverStyle(ResolverStyle.LENIENT)
        ),
        upvotes,
        clicks,
        deviceId ?: "",
        postMediaUrls.map { it.toPostMediaUrl() },
        postThreads.map { it.toPostThread() },
        postedBy.toPostedBy(postedById),
        liked,
        disliked
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