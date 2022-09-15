package com.briancaldera.hazel.repo

import com.google.firebase.BuildConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HazelRepository @Inject constructor(private val auth: FirebaseAuth) {
        init {
        // When running in debug mode, connect to the Firebase Emulator Suite.
        // "10.0.2.2" is a special IP address which allows the Android Emulator
        // to connect to "localhost" on the host computer. The port values (9xxx)
        // must match the values defined in the firebase.json file.
        if (BuildConfig.DEBUG) {
//            Firebase.database.useEmulator("10.0.2.2", 9000)
            Firebase.auth.useEmulator("10.0.2.2", 9099)
//            Firebase.storage.useEmulator("10.0.2.2", 9199)
        }
    }
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun signOutCurrentUser() {
        return auth.signOut()
    }
}