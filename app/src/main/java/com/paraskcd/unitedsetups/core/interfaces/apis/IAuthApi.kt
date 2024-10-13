package com.paraskcd.unitedsetups.core.interfaces.apis

import com.paraskcd.unitedsetups.core.entities.authentication.AuthApiEntity
import com.paraskcd.unitedsetups.core.requests.authentication.LoginRequest
import com.paraskcd.unitedsetups.core.requests.authentication.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthApi {
    @POST("auth/login")
    suspend fun Login(@Body request: LoginRequest): AuthApiEntity

    @POST("auth/register")
    suspend fun Register(@Body request: RegisterRequest): AuthApiEntity
}