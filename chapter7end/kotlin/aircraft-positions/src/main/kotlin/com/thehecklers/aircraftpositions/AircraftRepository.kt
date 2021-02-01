package com.thehecklers.aircraftpositions

import org.springframework.data.repository.CrudRepository

interface AircraftRepository: CrudRepository<Aircraft, String>