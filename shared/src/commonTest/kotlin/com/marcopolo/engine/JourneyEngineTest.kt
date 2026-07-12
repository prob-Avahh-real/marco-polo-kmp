package com.marcopolo.engine

import com.marcopolo.model.Journey
import com.marcopolo.model.Waypoint
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class JourneyEngineTest {

    @Test
    fun `computeStats should handle empty list`() {
        val stats = JourneyEngine().computeStats(emptyList())
        assertEquals(0, stats.totalJourneys)
        assertEquals(0.0, stats.totalDistanceKm)
        assertEquals(0.0, stats.averageDistanceKm)
    }

    @Test
    fun `computeStats should calculate correctly`() {
        val j1 = Journey(id = "1", title = "Trip 1", startDate = 0L, totalKm = 100.0)
        val j2 = Journey(id = "2", title = "Trip 2", startDate = 0L, totalKm = 200.0,
            route = listOf(Waypoint(0.0, 0.0, "A", 0L), Waypoint(1.0, 1.0, "B", 1L)))
        val stats = JourneyEngine().computeStats(listOf(j1, j2))
        assertEquals(2, stats.totalJourneys)
        assertEquals(300.0, stats.totalDistanceKm)
        assertEquals(2, stats.totalWaypoints)
        assertEquals(150.0, stats.averageDistanceKm)
        assertEquals(200.0, stats.longestJourneyKm)
        assertEquals(100.0, stats.shortestJourneyKm)
    }

    @Test
    fun `calculateDistance should return zero for same point`() {
        val result = JourneyEngine().calculateDistance(35.0, 139.0, 35.0, 139.0)
        assertEquals(0.0, result.km, 0.001)
    }

    @Test
    fun `calculateDistance should compute Tokyo to Osaka`() {
        val result = JourneyEngine().calculateDistance(35.6762, 139.6503, 34.6937, 135.5023)
        assertTrue(result.km > 400.0)
        assertTrue(result.km < 500.0)
    }

    @Test
    fun `estimateFuelCost should return reasonable value`() {
        val cost = JourneyEngine().estimateFuelCost(300.0, fuelPer100Km = 5.0, pricePerLiter = 8.5)
        // 300/100 * 5 * 8.5 = 127.5
        assertEquals(127.5, cost, 0.01)
    }

    @Test
    fun `estimateFuelCost should return zero for negative distance`() {
        val cost = JourneyEngine().estimateFuelCost(-10.0)
        assertEquals(0.0, cost)
    }

    @Test
    fun `estimateTravelTime should compute correctly`() {
        val hours = JourneyEngine().estimateTravelTime(300.0, avgSpeedKmh = 60.0)
        assertEquals(5.0, hours)
    }

    @Test
    fun `formatDuration should format hours and minutes`() {
        assertEquals("3h 30m", JourneyEngine().formatDuration(3.5))
        assertEquals("2h", JourneyEngine().formatDuration(2.0))
        assertEquals("45m", JourneyEngine().formatDuration(0.75))
        assertEquals("<1m", JourneyEngine().formatDuration(0.01))
    }

    @Test
    fun `filterByMinimumDistance should filter correctly`() {
        val j1 = Journey(id = "1", title = "Short", startDate = 0L, totalKm = 10.0)
        val j2 = Journey(id = "2", title = "Long", startDate = 0L, totalKm = 150.0)
        val result = JourneyEngine().filterByMinimumDistance(listOf(j1, j2), 100.0)
        assertEquals(1, result.size)
        assertEquals("Long", result[0].title)
    }

    @Test
    fun `searchJourneys should find by title`() {
        val j = Journey(id = "1", title = "Tokyo Adventure", startDate = 0L, notes = "Great trip")
        val result = JourneyEngine().searchJourneys(listOf(j), "adventure")
        assertEquals(1, result.size)
    }

    @Test
    fun `searchJourneys should find by notes`() {
        val j = Journey(id = "1", title = "Trip", startDate = 0L, notes = "Mount Fuji climb")
        val result = JourneyEngine().searchJourneys(listOf(j), "fuji")
        assertEquals(1, result.size)
    }
}
