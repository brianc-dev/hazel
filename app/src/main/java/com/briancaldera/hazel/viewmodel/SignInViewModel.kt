package com.briancaldera.hazel.viewmodel

import androidx.lifecycle.ViewModel
import com.briancaldera.hazel.repo.HazelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: HazelRepository
): ViewModel() {
    fun signIn() {

    }

    fun getCurrentUser() = repository.getCurrentUser()
}