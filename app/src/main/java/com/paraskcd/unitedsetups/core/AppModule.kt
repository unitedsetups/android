package com.paraskcd.unitedsetups.core

import android.content.Context
import com.paraskcd.unitedsetups.core.common.AuthInterceptor
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.core.common.TokenManager
import com.paraskcd.unitedsetups.core.interfaces.apis.IAuthApi
import com.paraskcd.unitedsetups.core.interfaces.apis.IPostApi
import com.paraskcd.unitedsetups.core.interfaces.apis.IPostThreadApi
import com.paraskcd.unitedsetups.core.interfaces.apis.IUploadApi
import com.paraskcd.unitedsetups.core.interfaces.apis.IUserApi
import com.paraskcd.unitedsetups.core.interfaces.repository.IAuthApiRepository
import com.paraskcd.unitedsetups.core.interfaces.repository.IPostApiRepository
import com.paraskcd.unitedsetups.core.interfaces.repository.IPostThreadApiRepository
import com.paraskcd.unitedsetups.core.interfaces.repository.IUploadApiRepository
import com.paraskcd.unitedsetups.core.interfaces.repository.IUserApiRepository
import com.paraskcd.unitedsetups.data.repository.authentication.AuthApiRepository
import com.paraskcd.unitedsetups.data.repository.posts.PostApiRepository
import com.paraskcd.unitedsetups.data.repository.postthreads.PostThreadsApiRepository
import com.paraskcd.unitedsetups.data.repository.upload.UploadApiRepository
import com.paraskcd.unitedsetups.data.repository.users.UserApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesTokenManager(@ApplicationContext appContext: Context) : TokenManager {
        return TokenManager(appContext)
    }

    @Singleton
    @Provides
    fun providesAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build()
    }

    @Singleton
    @Provides
    fun providesAuthApi(okHttpClient: OkHttpClient): IAuthApi {
        return Retrofit
                .Builder()
                .baseUrl(Constants.API)
                .client(okHttpClient)
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .build()
                .create(IAuthApi::class.java)
    }

    @Singleton
    @Provides
    fun providesAuthRepository(authApi: IAuthApi): IAuthApiRepository {
        return AuthApiRepository(authApi)
    }

    @Singleton
    @Provides
    fun providesPostApi(okHttpClient: OkHttpClient): IPostApi {
        return Retrofit
            .Builder()
            .baseUrl(Constants.API)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(IPostApi::class.java)
    }

    @Singleton
    @Provides
    fun providesPostRepository(postApi: IPostApi): IPostApiRepository {
        return PostApiRepository(postApi)
    }

    @Singleton
    @Provides
    fun providesUploadApi(okHttpClient: OkHttpClient): IUploadApi {
        return Retrofit
            .Builder()
            .baseUrl(Constants.API)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(IUploadApi::class.java)
    }

    @Singleton
    @Provides
    fun providesUploadRepository(@ApplicationContext appContext: Context, uploadApi: IUploadApi): IUploadApiRepository {
        return UploadApiRepository(uploadApi, appContext)
    }

    @Singleton
    @Provides
    fun providesUserApi(okHttpClient: OkHttpClient): IUserApi {
        return Retrofit
            .Builder()
            .baseUrl(Constants.API)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(IUserApi::class.java)
    }

    @Singleton
    @Provides
    fun providesUserRepository(userApi: IUserApi): IUserApiRepository {
        return UserApiRepository(userApi)
    }

    @Singleton
    @Provides
    fun providesPostThreadsApi(okHttpClient: OkHttpClient): IPostThreadApi {
        return Retrofit
            .Builder()
            .baseUrl(Constants.API)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(IPostThreadApi::class.java)
    }

    @Singleton
    @Provides
    fun providesPostThreadsApiRepository(postThreadApi: IPostThreadApi): IPostThreadApiRepository {
        return PostThreadsApiRepository(postThreadApi)
    }
}