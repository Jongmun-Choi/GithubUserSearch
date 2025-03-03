package com.dave.github.retrofit

import javax.inject.Inject

class AccessHelperImpl @Inject constructor(private val accessService: AccessService) : AccessHelper {

    override suspend fun getAccessToken(clientId: String, clientSecret: String, code: String)
                                                = accessService.getAccessToken(clientId, clientSecret, code)
}