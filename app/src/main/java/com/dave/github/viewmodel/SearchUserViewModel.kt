package com.dave.github.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.dave.github.model.User
import com.dave.github.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(application: Application, private val repository : ApiRepository): BaseViewModel(application) {

    private var pageCount = 1
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val user = _users.asStateFlow()

    fun searchUser(query: String) {
        viewModelScope.launch {
            repository.searchUser(query, pageCount)

        }
    }
}
