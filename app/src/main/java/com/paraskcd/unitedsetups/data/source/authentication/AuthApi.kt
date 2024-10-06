package com.paraskcd.unitedsetups.data.source.authentication

import com.paraskcd.unitedsetups.data.source.authentication.entity.AuthApiEntity
import com.paraskcd.unitedsetups.data.source.authentication.requests.LoginRequest
import com.paraskcd.unitedsetups.data.source.authentication.requests.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun Login(@Body request: LoginRequest): AuthApiEntity

    @POST("auth/register")
    suspend fun Register(@Body request: RegisterRequest): AuthApiEntity
}