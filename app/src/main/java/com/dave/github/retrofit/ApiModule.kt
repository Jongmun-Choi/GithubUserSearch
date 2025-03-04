package com.dave.github.retrofit


import com.dave.github.BuildConfig
import com.dave.github.repository.ApiRepository
import com.dave.github.repository.AuthRepository
import com.dave.github.util.EncryptedSharedPreferencesHelper
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class typeAccess

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class typeApi

    @Provides
    fun provideGithubUrl() = BuildConfig.ACCESS_URL

    @Provides
    fun provideGithubApiUrl() = BuildConfig.API_URL

    private val resultFactory = ResultFactory()

    private val interceptor =
        Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            val token = EncryptedSharedPreferencesHelper.getToken()
            if (!token.isNullOrEmpty()) {
                val bearText = "Bearer $token"
                requestBuilder.addHeader("Authorization", bearText)
            }
            chain.proceed(requestBuilder.build())
        }


    @Provides
    @Singleton
    @typeAccess
    fun provideAccessOkHttpClient() =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS).writeTimeout(150, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).addInterceptor(interceptor).build()
        } else {
            OkHttpClient.Builder().connectTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS).writeTimeout(150, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build()
        }



    @Singleton
    @Provides
    @typeAccess
    fun provideAccessRetrofit(@typeAccess okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder().build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(resultFactory)
            .baseUrl(provideGithubUrl())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @typeAccess
    fun provideAccessService(@typeAccess retrofit: Retrofit): AccessService = retrofit.create(AccessService::class.java)

    @Singleton
    @Provides
    @typeAccess
    fun provideAccessRepository(@typeAccess accessService: AccessService)= AuthRepository(accessService)

    @Provides
    @Singleton
    @typeApi
    fun provideApiOkHttpClient() =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS).writeTimeout(150, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).addInterceptor(interceptor).build()
        } else {
            OkHttpClient.Builder().connectTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS).writeTimeout(150, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build()
        }

    @Singleton
    @Provides
    @typeApi
    fun provideApiRetrofit(@typeApi okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder().build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(resultFactory)
            .baseUrl(provideGithubApiUrl())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @typeApi
    fun provideApiService(@typeApi retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    @typeApi
    fun provideApiRepository(@typeApi apiService: ApiService)= ApiRepository(apiService)

}

