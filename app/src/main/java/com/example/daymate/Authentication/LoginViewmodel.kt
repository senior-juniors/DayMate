package com.example.daymate.Authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewmodel:ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    var signInResult: String? = null
    var signUpResult: String? = null

    fun signup(email: String, password:String, confirmPassword:String){
        if(password!= confirmPassword){
            signUpResult= "Passwords do not match"
            return
        }

        viewModelScope.launch {
            try{
                firebaseAuth.createUserWithEmailAndPassword(email,password).await()
                val user =firebaseAuth.currentUser
                storeUserData(user)
                signUpResult="Sign up successful"
            }
            catch (e:Exception){
                signUpResult="Sign up failed: ${e.message}"
            }
        }
    }

    fun signIn(email: String,password: String){
        viewModelScope.launch {
            try {
                firebaseAuth.signInWithEmailAndPassword(email,password).await()
                val user=firebaseAuth.currentUser
                signInResult=getUserRole(user)
            }
            catch (e:Exception){
                signInResult= "Sign In Failed: ${e.message}"
            }
        }
    }

    private suspend fun storeUserData(user:FirebaseUser?){
        user?.let{
            val userData= hashMapOf("email" to it.email, "role" to "user")
            firestore.collection("users").document(it.uid).set(userData).await()
        }
    }

    private suspend fun getUserRole(user: FirebaseUser?): String{
        return try {
            val document:DocumentSnapshot?= user?.uid?.let {
                firestore.collection("users").document(it).get().await()
            }
            document?.getString("role")?:"User"
        }
        catch (e:Exception){
            "Error fetching user role: ${e.message}"
        }
    }
}