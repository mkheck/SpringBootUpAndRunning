package com.thehecklers.aircraftpositions

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PositionController(private val retriever: PositionRetriever) {
    @GetMapping("/aircraft")
    fun getCurrentAircraftPositions() = retriever.retrieveAircraftPositions("aircraft")

    @GetMapping("/aircraftadmin")
    fun getCurrentAircraftPositionsAdminPrivs() = retriever.retrieveAircraftPositions("aircraftadmin")
}
