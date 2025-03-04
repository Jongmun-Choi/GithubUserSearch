package com.dave.github.retrofit

import com.dave.github.model.User
import com.dave.github.model.UserSearchResult
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun searchUserList(query: String, page: Int): Result<UserSearchResult> = apiService.searchUser(query, page)
}