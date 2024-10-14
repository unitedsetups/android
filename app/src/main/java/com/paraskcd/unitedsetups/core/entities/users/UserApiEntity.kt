package com.paraskcd.unitedsetups.core.entities.users

import com.paraskcd.unitedsetups.domain.enums.UserRoles
import com.paraskcd.unitedsetups.domain.model.User

data class UserApiEntity(
    val id: String,
    val username: String,
    val email: String,
    val name: String,
    val telegramId: Long?,
    val profileImageUrl: String?,
    val profileImageThumbnailUrl: String?,
    val coverImageUrl: String?,
    val coverImageThumbnailUrl: String?,
    val role: String
)

fun UserApiEntity.toUser(): User {
    return User(
        id,
        username,
        email,
        name,
        telegramId,
        profileImageUrl,
        profileImageThumbnailUrl,
        coverImageUrl,
        coverImageThumbnailUrl,
        UserRoles.valueOf(role)
    )
}