package com.dave.github.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(

    @field:Json(name = "id")
    val id : Int,
    @field:Json(name = "login")
    val name : String,
    @field:Json(name = "avatar_url")
    val avatarUrl : String,
    @field:Json(name = "html_url")
    val repoUrl : String
)