package com.example.daymate.event

import androidx.compose.material3.CircularProgressIndicator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daymate.Event.EventRepository
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {
    private val repository = EventRepository()
    val events = repository.events

    fun addEvent(event: Event, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = repository.addEvent(event)
            if (result.isNotEmpty()) {
                onSuccess(result)
            } else {
                onError("Failed to add event")
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