package com.thehecklers.aircraftpositions

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface AircraftRepository: ReactiveCrudRepository<Aircraft, String>