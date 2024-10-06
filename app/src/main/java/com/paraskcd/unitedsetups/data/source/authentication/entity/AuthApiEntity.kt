package com.paraskcd.unitedsetups.data.source.authentication.entity

import com.paraskcd.unitedsetups.domain.model.Auth

data class AuthApiEntity(
    val id: String,
    val email: String,
    val token: String
)

fun AuthApiEntity.toAuth(): Auth {
    return Auth(
        id,
        email,
        token
    )
}