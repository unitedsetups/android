package com.paraskcd.unitedsetups.core.requests.posts

data class GetPostsRequest(
    val filter: String?,
    val page: Int,
    val pageSize: Int,
)

fun GetPostsRequest.toQueryMap(): Map<String, Any> {
    return mapOf(
        "filter" to (filter ?: ""),
        "page" to page,
        "pageSize" to pageSize
    )
}
