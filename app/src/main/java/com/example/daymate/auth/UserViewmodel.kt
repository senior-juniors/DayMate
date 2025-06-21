package com.example.daymate.auth

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserViewmodel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    var user_Data by mutableStateOf<UserData?>(null)
        private set


    // 🔄 Update academic info
    fun updateAcademicInfo(
        semester: String,
        branch: String,
        batch: String,
        onComplete: () -> Unit = {}
    ) {
        val user = auth.currentUser
        if (user == null) {
            Log.e("UPDATE_USER", "User not logged in")
            return
        }

        val updates = mapOf(
            "semester" to semester,
            "branch" to branch,
            "batch" to batch
        )

        db.collection("users").document(user.uid)
            .set(updates, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("UPDATE_USER", "Academic info updated")
                onComplete()
            }
            .addOnFailureListener {
                Log.e("UPDATE_USER", "Failed to update academic info: ${it.message}")
            }
    }

    // 🔄 Update club info
    fun updateClub(club: String, onComplete: () -> Unit = {}) {
        val user = auth.currentUser
        if (user == null) {
            Log.e("UPDATE_USER", "User not logged in")
            return
        }

        db.collection("users").document(user.uid)
            .set(mapOf("club" to club), SetOptions.merge())
            .addOnSuccessListener {
                Log.d("UPDATE_USER", "Club updated")
                onComplete()
            }
            .addOnFailureListener {
                Log.e("UPDATE_USER", "Failed to update club: ${it.message}")
            }
    }

    // 🔄 Update user name and email
    fun updateUserDetails(onComplete: () -> Unit = {}) {
        val user = auth.currentUser
        if (user == null) {
            Log.e("UPDATE_USER", "User not logged in")
            return
        }

        val name = user.displayName ?: ""
        val email = user.email ?: ""

        val updates = mapOf(
            "name" to name,
            "email" to email
        )

        db.collection("users").document(user.uid)
            .set(updates, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("UPDATE_USER", "User details updated from Firebase account")
                onComplete()
            }
            .addOnFailureListener {
                Log.e("UPDATE_USER", "Failed to update user details: ${it.message}")
            }
    }
    fun fetchTodaysTimetable(
        semester: String,
        branch: String,
        batch: String,
        onResult: (List<String>) -> Unit
    ) {
        val day = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())

        db.collection("TimeTable")
            .document(semester)
            .collection(branch)
            .document(batch)
            .collection("Monday")
            .document("Classes")
            .get()
            .addOnSuccessListener { dayDoc ->
                val list = mutableListOf<String>()
                for ((time, subject) in dayDoc.data.orEmpty()) {
                    list.add("$time - $subject")
                }
                onResult(list.sortedBy { it.substringBefore(" - ") })
            }
            .addOnFailureListener {
                Log.e("FIRESTORE", "Error fetching classes: ${it.message}")
                onResult(emptyList())
            }
    }


    // ✅ Check if user data exists
    fun checkUserDataExists(onResult: (Boolean) -> Unit) {
        val user = auth.currentUser
        if (user == null) {
            onResult(false)
            return
        }

        db.collection("users").document(user.uid)
            .get()
            .addOnSuccessListener { document ->
                onResult(document.exists())
            }
            .addOnFailureListener {
                Log.e("CHECK_USER", "Error checking user data: ${it.message}")
                onResult(false)
            }
    }



    // ✅ Fetch user data
    fun fetchUserData() {
        val user = auth.currentUser
        if (user == null) {
            Log.e("FETCH_USER", "User not logged in")
            return
        }

        db.collection("users").document(user.uid)
            .get()
            .addOnSuccessListener { document ->
                user_Data = document.toObject(UserData::class.java)
                Log.d("FETCH_USER", "User data fetched")
            }
            .addOnFailureListener {
                Log.e("FETCH_USER", "Error fetching user data: ${it.message}")
            }

    }

    // ✅ Sign out
    fun signOut() {
        auth.signOut()
        user_Data = null
        Log.d("Auth", "User signed out successfully")
    }
}

data class UserData(
    val name: String = "",
    val email: String = "",
    val semester: String = "",
    val branch: String = "",
    val batch: String = "",
    val club: String = ""
)
