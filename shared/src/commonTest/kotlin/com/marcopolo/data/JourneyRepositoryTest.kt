package com.marcopolo.data

import com.marcopolo.model.Journey
import com.marcopolo.model.Waypoint
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class InMemoryJourneyRepositoryTest {

    @Test
    fun `add should persist journey`() = kotlinx.coroutines.test.runTest {
        val repo = InMemoryJourneyRepository()
        val journey = Journey(id = "1", title = "Test Ride", startDate = 1000L, totalKm = 50.0)
        repo.add(journey)
        val loaded = repo.getById("1")
        assertNotNull(loaded)
        assertEquals("Test Ride", loaded.title)
    }

    @Test
    fun `update should modify journey`() = kotlinx.coroutines.test.runTest {
        val repo = InMemoryJourneyRepository()
        repo.add(Journey(id = "1", title = "Original", startDate = 0L))
        repo.update(Journey(id = "1", title = "Updated", startDate = 0L))
        assertEquals("Updated", repo.getById("1")?.title)
    }

    @Test
    fun `delete should remove journey`() = kotlinx.coroutines.test.runTest {
        val repo = InMemoryJourneyRepository()
        repo.add(Journey(id = "1", title = "Trip", startDate = 0L))
        repo.delete("1")
        assertNull(repo.getById("1"))
    }

    @Test
    fun `getAll should return all journeys`() = kotlinx.coroutines.test.runTest {
        val repo = InMemoryJourneyRepository()
        repo.add(Journey(id = "1", title = "A", startDate = 0L))
        repo.add(Journey(id = "2", title = "B", startDate = 0L))
        assertEquals(2, repo.getAll().size)
    }

    @Test
    fun `clear should remove all journeys`() = kotlinx.coroutines.test.runTest {
        val repo = InMemoryJourneyRepository()
        repo.add(Journey(id = "1", title = "A", startDate = 0L))
        repo.clear()
        assertEquals(0, repo.getAll().size)
    }

    @Test
    fun `observeJourneys should emit updates`() = kotlinx.coroutines.test.runTest {
        val repo = InMemoryJourneyRepository()
        val j = Journey(id = "1", title = "Trip", startDate = 0L)
        repo.add(j)
        val observed = repo.observeJourneys()
        assertEquals(1, observed.size)
    }
}
