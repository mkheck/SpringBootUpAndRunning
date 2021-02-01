package com.thehecklers.aircraftpositions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class AircraftRepositoryTest {
    @Autowired
    private AircraftRepository repository;

    private Aircraft ac1, ac2;

    @BeforeEach
    void setUp() {
        // Spring Airlines flight 001 en route, flying STL to SFO,
        // at 30000' currently over Kansas City
        ac1 = new Aircraft(1L, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SFO", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        // Spring Airlines flight 002 en route, flying SFO to STL,
        // at 40000' currently over Denver
        ac2 = new Aircraft(2L, "SAL002", "sqwk", "N54321", "SAL002",
                "SFO-STL", "LJ", "ct",
                40000, 65, 440, 0, 0,
                39.8560963, -104.6759263, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        repository.saveAll(List.of(ac1, ac2));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void testFindById() {
        assertEquals(Optional.of(ac1), repository.findById(ac1.getId()));
        assertEquals(Optional.of(ac2), repository.findById(ac2.getId()));
    }

    @Test
    void testFindAll() {
        assertEquals(List.of(ac1, ac2), repository.findAll());
    }
}