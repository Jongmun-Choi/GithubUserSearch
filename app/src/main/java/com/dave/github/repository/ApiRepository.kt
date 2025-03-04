package com.dave.github.repository

import com.dave.github.retrofit.ApiModule
import com.dave.github.retrofit.ApiService
import javax.inject.Inject

class ApiRepository @Inject constructor(@ApiModule.typeApi private val apiService: ApiService) {
    suspend fun searchUser(query: String, pageNo: Int) = apiService.searchUser(query, pageNo)
}