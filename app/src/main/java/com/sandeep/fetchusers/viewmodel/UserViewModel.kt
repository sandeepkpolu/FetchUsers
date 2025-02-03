package com.sandeep.fetchusers.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandeep.fetchusers.data.UserRepository
import com.sandeep.fetchusers.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _usersStatus = MutableStateFlow<Status<List<User>>>(Status.Loading)
    val usersStatus: StateFlow<Status<List<User>>> get() = _usersStatus

    var job: Job? = null

    fun fetchUsers() {
        job = viewModelScope.launch {
            _usersStatus.value = Status.Loading
            try {
                val posts = userRepository.getUsers()
                _usersStatus.value = Status.Success(posts)
            } catch (e: Exception) {
                _usersStatus.value = Status.Error(e.message ?: "An Error occurred")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun refreshPosts() {
        fetchUsers()
    }

}