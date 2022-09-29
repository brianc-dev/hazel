package com.briancaldera.hazel.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.briancaldera.hazel.MainActivity
import com.briancaldera.hazel.R
import com.briancaldera.hazel.viewmodel.SignInViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity: AppCompatActivity() {
    companion object {
        private const val TAG = "SignInActivity"
    }
    private val viewModel: SignInViewModel by viewModels ()

    private val signInLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
            onSignInResult(it)
        }

    override fun onStart() {
        super.onStart()
        if (viewModel.getCurrentUser() != null) {
            goToHomeActivity()
        } else {

            val signInIntent = AuthUI.getInstance()
                .apply { useEmulator("192.168.1.106", 9099) }
                .createSignInIntentBuilder()
                .setLogo(R.mipmap.ic_launcher)
                .setAvailableProviders(listOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()
                ))
                .build()

            signInLauncher.launch(signInIntent)
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            Log.d(TAG, "Sign in successful!")
        } else {
            Toast.makeText(
                this,
                "There was an error signing in",
                Toast.LENGTH_LONG).show()
            val response = result.idpResponse
            if (response == null) {
                Log.w(TAG, "Sign in canceled")
            } else {
                Log.w(TAG, "Sign in error", response.error)
            }
        }
    }

    private fun goToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        this.startActivity(intent)
    }
}