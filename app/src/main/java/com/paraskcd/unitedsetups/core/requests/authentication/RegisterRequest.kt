package com.paraskcd.unitedsetups.core.requests.authentication

data class RegisterRequest(
    val username: String,
    val email: String,
    val name: String,
    val password: String
)
