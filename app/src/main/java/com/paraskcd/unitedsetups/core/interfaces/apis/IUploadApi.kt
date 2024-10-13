package com.paraskcd.unitedsetups.core.interfaces.apis

import com.paraskcd.unitedsetups.core.entities.uploads.UploadApiEntity
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface IUploadApi {
    @Multipart
    @POST("upload/post-media")
    suspend fun uploadPostMedia(@Part files: List<MultipartBody.Part>): UploadApiEntity

    @Multipart
    @POST("upload/profile-picture")
    suspend fun uploadProfileImage(@Part files: List<MultipartBody.Part>): UploadApiEntity

    @Multipart
    @POST("upload/profile-cover-picture")
    suspend fun UploadProfileCoverImage(@Part files: List<MultipartBody.Part>): UploadApiEntity
}