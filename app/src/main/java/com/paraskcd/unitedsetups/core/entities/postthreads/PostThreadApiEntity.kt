package com.paraskcd.unitedsetups.core.entities.postthreads

import com.paraskcd.unitedsetups.core.entities.posts.PostMediaUrlResponse
import com.paraskcd.unitedsetups.core.entities.posts.PostedByResponse
import com.paraskcd.unitedsetups.core.entities.posts.toPostMediaUrl
import com.paraskcd.unitedsetups.core.entities.posts.toPostedBy
import com.paraskcd.unitedsetups.domain.model.PostThread
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

data class PostThreadApiEntity(
    val id: String,
    val text: String,
    val createdDateTime: String?,
    val updatedDateTime: String?,
    val upvotes: Int,
    val clicks: Int,
    val postMediaUrls: List<PostMediaUrlResponse>,
    val postedById: String,
    val postedBy: PostedByResponse,
    val liked: Boolean,
    val disliked: Boolean,
    val postId: String?,
    val parentPostThreadId: String?,
    val childrenPostThreads: List<PostThreadApiEntity>
)

fun PostThreadApiEntity.toPostThread(): PostThread {
    return PostThread(
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
        postMediaUrls.map { it.toPostMediaUrl() },
        postedBy.toPostedBy(postedById),
        liked,
        disliked,
        postId,
        parentPostThreadId,
        childrenPostThreads.map { it.toPostThread() }
    )
}