package com.briancaldera.hazel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.briancaldera.hazel.repo.HazelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: HazelRepository
): ViewModel() {
    fun signIn() {

    }

    fun getCurrentUser() = repository.getCurrentUser()

    fun signOut() = repository.signOutCurrentUser()
}