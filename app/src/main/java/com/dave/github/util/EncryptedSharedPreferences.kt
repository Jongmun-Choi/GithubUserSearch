package com.dave.github.util

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.dave.github.BuildConfig
import com.dave.github.GitHubSearchApplication.Companion.context
import com.dave.github.model.AccessToken

object EncryptedSharedPreferencesHelper {
    private val masterKeyAlias = MasterKey
        .Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private val tokenSharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        BuildConfig.Shared_Preference_Name,
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun setToken(tokenInfo : AccessToken) {
        tokenSharedPreferences.edit().putString("accessToken", tokenInfo.accessToken).apply()
    }

    fun getToken() : String? {
        return tokenSharedPreferences.getString("accessToken", "")
    }
}