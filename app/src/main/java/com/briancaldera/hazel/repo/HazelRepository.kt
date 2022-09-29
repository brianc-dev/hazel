package com.briancaldera.hazel.repo

import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.google.firebase.BuildConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class HazelRepository @Inject constructor() {

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        // When running in debug mode, connect to the Firebase Emulator Suite.
        // "10.0.2.2" is a special IP address which allows the Android Emulator
        // to connect to "localhost" on the host computer. The port values (9xxx)
        // must match the values defined in the firebase.json file.
        Log.i("DEBUG MODE:", "${BuildConfig.DEBUG}")

            Firebase.auth.useEmulator("192.168.1.106", 9099)
        if (BuildConfig.DEBUG) {
//            Firebase.database.useEmulator("10.0.2.2", 9000)
//            Firebase.auth.useEmulator("192.168.1.106", 9099)
//            Firebase.storage.useEmulator("10.0.2.2", 9199)
        }
    }

    //noinspection RestrictedApi
    fun getCurrentUser(): FirebaseUser? = AuthUI.getInstance().auth.currentUser

    //noinspection RestrictedApi
    fun signOutCurrentUser() = AuthUI.getInstance().signOut(AuthUI.getApplicationContext().applicationContext)
}