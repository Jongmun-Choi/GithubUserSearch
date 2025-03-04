package com.dave.github.retrofit

import com.dave.github.model.User
import com.dave.github.model.UserSearchResult

interface ApiHelper {

    // 유저검색
    suspend fun searchUserList(query: String, page: Int): Result<UserSearchResult>
}