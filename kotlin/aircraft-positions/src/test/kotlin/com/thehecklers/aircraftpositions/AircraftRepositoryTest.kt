package com.thehecklers.aircraftpositions

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.Instant
import java.util.*

@DataJpaTest
internal class AircraftRepositoryTest {
    @Autowired
    private lateinit var repository: AircraftRepository

    private lateinit var ac1: Aircraft
    private lateinit var ac2: Aircraft

    @BeforeEach
    fun setUp() {
        // Spring Airlines flight 001 en route, flying STL to SFO,
        // at 30000' currently over Kansas City
        ac1 = Aircraft(
            1L, "SAL001", "sqwk", "N12345", "SAL001",
            "STL-SFO", "LJ", "ct",
            30000, 280, 440, 0, 0,
            39.2979849, -94.71921, 0.0, 0.0, 0.0,
            true, false,
            Instant.now(), Instant.now(), Instant.now()
        )

        // Spring Airlines flight 002 en route, flying SFO to STL,
        // at 40000' currently over Denver
        ac2 = Aircraft(
            2L, "SAL002", "sqwk", "N54321", "SAL002",
            "SFO-STL", "LJ", "ct",
            40000, 65, 440, 0, 0,
            39.8560963, -104.6759263, 0.0, 0.0, 0.0,
            true, false,
            Instant.now(), Instant.now(), Instant.now()
        )
        repository.saveAll(listOf(ac1, ac2))
    }

    @AfterEach
    fun tearDown() {
        repository.deleteAll()
    }

    @Test
    fun testFindById() {
        Assertions.assertEquals(Optional.of(ac1), repository.findById(ac1.id!!))
        Assertions.assertEquals(Optional.of(ac2), repository.findById(ac2.id!!))
    }

    @Test
    fun testFindAll() {
        Assertions.assertEquals(listOf(ac1, ac2), repository.findAll())
    }
}