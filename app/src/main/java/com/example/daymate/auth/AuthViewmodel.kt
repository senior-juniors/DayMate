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
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val email = user?.email
                    
                    // Validate college email domain or admin email
                    val isCollegeEmail = email?.endsWith("@iiitkota.ac.in") == true
                    val isAdminEmail = email == "hrmeenam636@gmail.com"
                    
                    if (email == null || (!isCollegeEmail && !isAdminEmail)) {
                        // Sign out the user if email doesn't match
                        auth.signOut()
                        onResult(false)
                    } else {
                        onResult(true)
                    }
                } else {
                    onResult(false)
                }
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
