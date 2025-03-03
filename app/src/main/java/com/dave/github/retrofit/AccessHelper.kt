package com.dave.github.retrofit

import com.dave.github.model.AccessToken

interface AccessHelper {

    suspend fun getAccessToken(clientId: String, clientSecret: String, code: String): Result<AccessToken>

}