package com.marcopolo.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JourneyTest {

    @Test
    fun `journey should have default values`() {
        val j = Journey(id = "1", title = "Test", startDate = 0L)
        assertEquals(0.0, j.totalKm)
        assertEquals(0, j.route.size)
        assertEquals(0, j.photos.size)
        assertEquals("", j.notes)
    }

    @Test
    fun `waypoint should store coordinates`() {
        val wp = Waypoint(lat = 35.6762, lng = 139.6503, label = "Tokyo", timestamp = 1000L)
        assertEquals(35.6762, wp.lat)
        assertEquals("Tokyo", wp.label)
    }

    @Test
    fun `achievement should have optional unlockedAt`() {
        val a = Achievement(id = "a1", name = "First Ride", description = "Complete first ride", icon = "🏍")
        assertNull(a.unlockedAt)
    }

    @Test
    fun `achievement with unlockedAt should show completion`() {
        val a = Achievement(id = "a2", name = "Century", description = "Ride 100km", icon = "🎯", unlockedAt = 1000L)
        assertNotNull(a.unlockedAt)
    }

    @Test
    fun `journey with end date should have valid duration`() {
        val j = Journey(id = "1", title = "Trip", startDate = 0L, endDate = 3600000L)
        assertTrue(j.endDate!! > j.startDate)
    }
}
