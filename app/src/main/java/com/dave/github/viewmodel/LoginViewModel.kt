package com.dave.github.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.dave.github.BuildConfig
import com.dave.github.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(application:Application, private val repository : AuthRepository): BaseViewModel(application)
{

    private val _login = MutableStateFlow(false)
    val login: StateFlow<Boolean> = _login.asStateFlow()

    fun getAccessToken(code: String) {
        viewModelScope.launch {
            try {
                repository.getAccessToken(BuildConfig.clientId, BuildConfig.clientSecret, code)
                    .onSuccess {
                        System.out.println("accessToken = ${it.accessToken}")
                        _login.emit(true)
                    }
                    .onFailure {
                        _login.emit(false)
                    }
            } catch (e: UnknownHostException) {

            } catch (e: SocketTimeoutException) {
            }
        }
    }

}