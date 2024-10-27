package com.paraskcd.unitedsetups.domain.model

import java.time.LocalDateTime

data class PostThread(
    val id: String,
    val text: String,
    val createdDateTime: LocalDateTime,
    val updatedDateTime: LocalDateTime,
    var upvotes: Int,
    val clicks: Int,
    val postMediaUrls: List<PostMediaUrl>,
    val postedBy: PostedBy,
    var liked: Boolean,
    var disliked: Boolean,
    val postId: String?,
    val parentPostThreadId: String?,
    val childrenPostThreads: List<PostThread>
)
