package com.dave.github.di

import com.dave.github.retrofit.ApiHelper
import com.dave.github.retrofit.ApiHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {
    @Binds
    @Singleton
    abstract fun bindApiHelper(apiHelper: ApiHelperImpl) : ApiHelper
}