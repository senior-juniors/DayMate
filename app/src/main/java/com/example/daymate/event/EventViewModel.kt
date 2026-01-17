package com.example.daymate.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daymate.Event.EventRepository
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {
    private val repository = EventRepository()
    val events = repository.events

    fun addEvent(event: Event, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        println("EventViewModel: addEvent called for event: ${event.title}")
        viewModelScope.launch {
            try {
                println("EventViewModel: Calling repository.addEvent")
                val result = repository.addEvent(event)
                println("EventViewModel: Repository returned result: $result")
                if (result.isNotEmpty()) {
                    println("EventViewModel: Success - calling onSuccess callback")
                    onSuccess(result)
                } else {
                    println("EventViewModel: Failed - empty result")
                    onError("Failed to add event")
                }
            } catch (e: Exception) {
                println("EventViewModel: Exception caught - ${e.message}")
                e.printStackTrace()
                onError("Exception: ${e.message}")
            }
        }
    }

    fun deleteEvent(eventId: String) {
        viewModelScope.launch {
            try {
                repository.deleteEvent(eventId)

                println("Event deleted successfully")

            } catch (e: Exception) {
                println("Error deleting event: ${e.message}")
            }
        }
    }
}