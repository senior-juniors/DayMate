package com.example.daymate.auth

import android.content.Context
import com.example.daymate.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope

object GoogleAuthManager {
    fun getClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .requestScopes(
                Scope("https://www.googleapis.com/auth/classroom.courses.readonly"),
                Scope("https://www.googleapis.com/auth/classroom.coursework.me.readonly")
            )
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    fun signOut(context: Context, onComplete: () -> Unit) {
        val client = getClient(context)
        client.signOut().addOnCompleteListener {
            onComplete()
        }
    }

}