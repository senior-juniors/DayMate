package com.example.daymate.Event

import com.example.daymate.event.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EventRepository {
    private val database = Firebase.database("https://daymate-e74c9-default-rtdb.firebaseio.com/").reference.child("events")

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    init {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val eventsList = mutableListOf<Event>()
                for (eventSnapshot in snapshot.children) {
                    val event = eventSnapshot.getValue<Event>()?.copy(id = eventSnapshot.key ?: "")
                    event?.let { eventsList.add(it) }
                }
                _events.value = eventsList.sortedBy { it.date } // Sort by date
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
                println("Firebase Error: ${error.message}")
            }
        })
    }

    suspend fun addEvent(event: Event): String {
        return try {
            val eventRef = database.push()
            eventRef.setValue(event.copy(id = eventRef.key ?: ""))
            eventRef.key ?: ""
        } catch (e: Exception) {
            println("Error adding event: ${e.message}")
            ""
        }
    }

    // Add this function if you need to update events
    suspend fun updateEvent(event: Event): Boolean {
        return try {
            database.child(event.id).setValue(event)
            true
        } catch (e: Exception) {
            println("Error updating event: ${e.message}")
            false
        }
    }

    // Add this function if you need to delete events
    suspend fun deleteEvent(eventId: String): Boolean {
        return try {
            database.child(eventId).removeValue()
            true
        } catch (e: Exception) {
            println("Error deleting event: ${e.message}")
            false
        }
    }
}