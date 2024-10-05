package com.paraskcd.unitedsetups.core

import android.content.Context
import com.paraskcd.unitedsetups.core.common.AuthInterceptor
import com.paraskcd.unitedsetups.core.common.Constants
import com.paraskcd.unitedsetups.core.common.TokenManager
import com.paraskcd.unitedsetups.data.repository.authentication.AuthApiRepository
import com.paraskcd.unitedsetups.data.source.authentication.AuthApi
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
    fun providesAuthApi(okHttpClient: OkHttpClient): AuthApi {
        return Retrofit
                .Builder()
                .baseUrl(Constants.API)
                .client(okHttpClient)
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .build()
                .create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun providesAuthRepository(authApi: AuthApi): AuthApiRepository {
        return AuthApiRepository(authApi)
    }
}