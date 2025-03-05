package com.dave.github.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.dave.github.model.User
import com.dave.github.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(application: Application, private val repository : ApiRepository): BaseViewModel(application) {

    private var pageCount = 1
    private val _users = MutableStateFlow<MutableList<User>>(mutableListOf())
    val user : StateFlow<MutableList<User>>
        get() = _users.asStateFlow()

    fun searchUser(query: String, listReset: Boolean = false) {
        if (listReset){
            pageCount = 1
            _users.value.clear()
        } else pageCount++

        viewModelScope.launch {
            repository.searchUser(query, pageCount).onSuccess { result ->
                val userList = _users.value.toMutableList()
                userList.addAll(result.items)
                _users.emit(userList)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}
