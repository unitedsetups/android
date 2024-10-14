package com.paraskcd.unitedsetups.core.interfaces.apis

import com.paraskcd.unitedsetups.core.entities.users.UserApiEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface IUserApi {
    @GET("users/{userId}")
    suspend fun getUserById(@Path("userId") userId: String): UserApiEntity

    @GET("users/me")
    suspend fun getMyProfile(): UserApiEntity
}