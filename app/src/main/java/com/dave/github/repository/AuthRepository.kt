package com.dave.github.repository

import com.dave.github.retrofit.AccessService
import com.dave.github.retrofit.ApiModule
import jakarta.inject.Inject

class AuthRepository @Inject constructor(@ApiModule.typeAccess private val accessService: AccessService) {

    suspend fun getAccessToken(clientId:String, clientSecret: String, code:String) = accessService.getAccessToken(clientId, clientSecret, code)

}