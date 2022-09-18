package com.briancaldera.hazel

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.briancaldera.hazel.ui.theme.HazelTheme
import com.briancaldera.hazel.viewmodel.SignInViewModel
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: SignInViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HazelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    SignOut {
                        AuthUI.getInstance()
                            .signOut(this)
                            .addOnCompleteListener{
                                if (it.isSuccessful) {
                                    if (Firebase.auth.currentUser == null) {
                                        Toast.makeText(this, "User successfully signed out!", Toast.LENGTH_SHORT).show()
                                        finish()
                                    }
                                }
                            }
                    }
                }
            }
        }
    }

//    override fun onStart() {
//        super.onStart()
//        if (viewModel.getCurrentUser() == null) finish()
//    }
}

@Composable
fun SignOut(signOutListener: () -> Unit) {
    Button(onClick = signOutListener, modifier = Modifier
    ) {
        Text(text = "Sign out")
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SignOutPreview() {
    HazelTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            SignOut {
            }

        }
    }
}
