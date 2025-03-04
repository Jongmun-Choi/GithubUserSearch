package com.dave.github.retrofit

import com.dave.github.model.UserSearchResult
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("/search/users")
    suspend fun searchUser(@Query("q") query: String,
               @Query("page") page: Int = 1,
               @Query("per_page") perPage: Int = 20): Result<UserSearchResult>

}