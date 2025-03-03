package com.dave.github.di

import com.dave.github.retrofit.AccessHelper
import com.dave.github.retrofit.AccessHelperImpl
import com.dave.github.retrofit.ApiHelper
import com.dave.github.retrofit.ApiHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

//    @Binds
//    @Singleton
//    abstract fun bindsAccessHelper(accessHelperImpl: AccessHelperImpl): AccessHelper
//
//    @Binds
//    @Singleton
//    abstract fun bindsApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper

}