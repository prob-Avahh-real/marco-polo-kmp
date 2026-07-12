package com.marcopolo.engine

import com.marcopolo.model.Journey
import com.marcopolo.model.Waypoint
import com.marcopolo.model.Achievement

data class JourneyStats(
    val totalJourneys: Int,
    val totalDistanceKm: Double,
    val totalWaypoints: Int,
    val totalPhotos: Int,
    val longestJourneyKm: Double,
    val shortestJourneyKm: Double,
    val averageDistanceKm: Double,
    val averageWaypointsPerJourney: Double
)

data class DistanceResult(
    val km: Double,
    val label: String
)

class JourneyEngine {

    fun computeStats(journeys: List<Journey>): JourneyStats {
        val distances = journeys.map { it.totalKm }
        return JourneyStats(
            totalJourneys = journeys.size,
            totalDistanceKm = journeys.sumOf { it.totalKm },
            totalWaypoints = journeys.sumOf { it.route.size },
            totalPhotos = journeys.sumOf { it.photos.size },
            longestJourneyKm = distances.maxOrNull() ?: 0.0,
            shortestJourneyKm = distances.minOrNull() ?: 0.0,
            averageDistanceKm = if (distances.isNotEmpty()) distances.average() else 0.0,
            averageWaypointsPerJourney = if (journeys.isNotEmpty())
                journeys.sumOf { it.route.size }.toDouble() / journeys.size else 0.0
        )
    }

    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): DistanceResult {
        val earthRadius = 6371.0 // km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val km = earthRadius * c
        val label = when {
            km < 1.0 -> "${"%.0f".format(km * 1000)} m"
            km < 100.0 -> "${"%.1f".format(km)} km"
            else -> "${"%.0f".format(km)} km"
        }
        return DistanceResult(km = km, label = label)
    }

    fun estimateFuelCost(distanceKm: Double, fuelPer100Km: Double = 5.0, pricePerLiter: Double = 8.5): Double {
        if (distanceKm < 0 || fuelPer100Km <= 0 || pricePerLiter <= 0) return 0.0
        val liters = distanceKm / 100.0 * fuelPer100Km
        return liters * pricePerLiter
    }

    fun estimateTravelTime(distanceKm: Double, avgSpeedKmh: Double = 60.0): Double {
        if (distanceKm <= 0 || avgSpeedKmh <= 0) return 0.0
        return distanceKm / avgSpeedKmh
    }

    fun formatDuration(hours: Double): String {
        val h = hours.toInt()
        val m = ((hours - h) * 60).toInt()
        return when {
            h > 0 && m > 0 -> "${h}h ${m}m"
            h > 0 -> "${h}h"
            m > 0 -> "${m}m"
            else -> "<1m"
        }
    }

    fun filterByMinimumDistance(journeys: List<Journey>, minKm: Double): List<Journey> =
        journeys.filter { it.totalKm >= minKm }

    fun searchJourneys(journeys: List<Journey>, query: String): List<Journey> {
        val q = query.lowercase()
        return journeys.filter {
            it.title.lowercase().contains(q) || it.notes.lowercase().contains(q)
        }
    }
}
