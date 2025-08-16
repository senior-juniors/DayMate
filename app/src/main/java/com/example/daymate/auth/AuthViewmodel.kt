package com.example.daymate.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthViewmodel:ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    fun signInWithGoogle(idToken: String, onResult: (Boolean) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }

    fun signOut(context: Context, onComplete: () -> Unit) {
        auth.signOut()
        GoogleAuthManager.signOut(context) {
            onComplete()
        }
    }


    fun getCurrentUser() = auth.currentUser
}
