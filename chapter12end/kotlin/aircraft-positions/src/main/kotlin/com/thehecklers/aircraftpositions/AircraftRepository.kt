package com.thehecklers.aircraftpositions

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface AircraftRepository: ReactiveCrudRepository<Aircraft, String> {
    fun findAircraftByReg(reg: String): Flux<Aircraft>
}