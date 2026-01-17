package com.example.daymate.Event

import com.example.daymate.event.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class EventRepository {
    private val database: DatabaseReference

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    init {
        // Get database instance and enable persistence
        val firebaseDatabase = Firebase.database("https://daymate-e74c9-default-rtdb.firebaseio.com/")
        try {
            firebaseDatabase.setPersistenceEnabled(true)
            println("EventRepository: Firebase persistence enabled")
        } catch (e: Exception) {
            println("EventRepository: Persistence already enabled or error: ${e.message}")
        }

        database = firebaseDatabase.reference.child("events")

        println("EventRepository: Initializing - Database reference: ${database.path}")
        println("EventRepository: Database URL: https://daymate-e74c9-default-rtdb.firebaseio.com/events")

        // Monitor connection status
        val connectedRef = Firebase.database("https://daymate-e74c9-default-rtdb.firebaseio.com/").getReference(".info/connected")
        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java) ?: false
                println("EventRepository: Firebase connected: $connected")
            }
            override fun onCancelled(error: DatabaseError) {
                println("EventRepository: Connection check cancelled: ${error.message}")
            }
        })

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val eventsList = mutableListOf<Event>()
                println("Firebase Events: onDataChange triggered, snapshot exists: ${snapshot.exists()}, children count: ${snapshot.childrenCount}")
                for (eventSnapshot in snapshot.children) {
                    val event = eventSnapshot.getValue<Event>()?.copy(id = eventSnapshot.key ?: "")
                    event?.let {
                        eventsList.add(it)
                        println("Firebase Events: Loaded event - ${it.title}")
                    }
                }
                _events.value = eventsList.sortedBy { it.date } // Sort by date
                println("Firebase Events: Total events loaded: ${eventsList.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
                println("Firebase Events Error: ${error.message}")
            }
        })
    }

    suspend fun addEvent(event: Event): String {
        return try {
            println("Firebase Events: Attempting to add event - ${event.title}")
            val eventRef = database.push()
            val eventId = eventRef.key ?: ""
            println("Firebase Events: Generated event ID: $eventId")
            eventRef.setValue(event.copy(id = eventId)).await()
            println("Firebase Events: Successfully added event to Firebase")
            eventId
        } catch (e: Exception) {
            println("Firebase Events Error adding event: ${e.message}")
            e.printStackTrace()
            ""
        }
    }

    // Add this function if you need to update events
    suspend fun updateEvent(event: Event): Boolean {
        return try {
            database.child(event.id).setValue(event).await()
            true
        } catch (e: Exception) {
            println("Error updating event: ${e.message}")
            false
        }
    }

    // Add this function if you need to delete events
    suspend fun deleteEvent(eventId: String): Boolean {
        return try {
            println("Firebase Events: Attempting to delete event ID: $eventId")
            database.child(eventId).removeValue().await()
            println("Firebase Events: Successfully deleted event")
            true
        } catch (e: Exception) {
            println("Firebase Events Error deleting event: ${e.message}")
            e.printStackTrace()
            false
        }
    }
}