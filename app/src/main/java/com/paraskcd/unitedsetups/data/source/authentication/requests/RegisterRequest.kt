package com.paraskcd.unitedsetups.data.source.authentication.requests

data class RegisterRequest(
    val username: String,
    val email: String,
    val name: String,
    val password: String
)
