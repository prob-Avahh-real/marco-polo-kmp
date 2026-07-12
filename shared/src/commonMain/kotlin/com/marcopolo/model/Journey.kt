// Journey.kt — marco-polo motorcycle travel models
package com.marcopolo.model

data class Journey(
    val id: String,
    val title: String,
    val startDate: Long,
    val endDate: Long? = null,
    val totalKm: Double = 0.0,
    val route: List<Waypoint> = emptyList(),
    val photos: List<String> = emptyList(),
    val notes: String = ""
)

data class Waypoint(
    val lat: Double,
    val lng: Double,
    val label: String,
    val timestamp: Long
)

data class Achievement(
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val unlockedAt: Long? = null
)
