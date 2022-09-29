package com.briancaldera.hazel.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.briancaldera.hazel.repo.HazelRepository
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: HazelRepository
): ViewModel() {

    var uiState: MutableState<UserState>
        private set

    init {
        uiState = mutableStateOf(UserState(getCurrentUser()?.displayName.toString()))
    }

    val signOut: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getCurrentUser() = repository.getCurrentUser()

    fun signOut(callback: (signOutSuccessful: Boolean, e: Exception?) -> Unit) {
        val task =  repository.signOutCurrentUser()
        task.addOnSuccessListener {
                signOut.value = true
        }.addOnFailureListener { exception ->
            callback(false, exception)
        }
    }
}

data class UserState(
    val username: String,
)