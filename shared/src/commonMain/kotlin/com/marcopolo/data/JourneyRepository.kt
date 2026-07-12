package com.marcopolo.data

import com.marcopolo.model.Journey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface IJourneyRepository {
    fun observeJourneys(): Flow<List<Journey>>
    suspend fun add(journey: Journey)
    suspend fun update(journey: Journey)
    suspend fun delete(id: String)
    suspend fun getById(id: String): Journey?
    suspend fun getAll(): List<Journey>
    suspend fun clear()
}

class InMemoryJourneyRepository : IJourneyRepository {
    private val mutex = Mutex()
    private val _journeys = MutableStateFlow<List<Journey>>(emptyList())

    override fun observeJourneys(): Flow<List<Journey>> = _journeys.asStateFlow()

    override suspend fun add(journey: Journey) = mutex.withLock {
        _journeys.value = _journeys.value + journey
    }

    override suspend fun update(journey: Journey) = mutex.withLock {
        _journeys.value = _journeys.value.map { if (it.id == journey.id) journey else it }
    }

    override suspend fun delete(id: String) = mutex.withLock {
        _journeys.value = _journeys.value.filter { it.id != id }
    }

    override suspend fun getById(id: String): Journey? = mutex.withLock {
        _journeys.value.find { it.id == id }
    }

    override suspend fun getAll(): List<Journey> = mutex.withLock { _journeys.value }

    override suspend fun clear() = mutex.withLock { _journeys.value = emptyList() }
}
