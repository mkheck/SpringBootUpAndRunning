package com.thehecklers.aircraftpositions

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux

@Service
class PositionService(private val repo: AircraftRepository) {
    private val client = WebClient.create("http://localhost:7634/aircraft")

    fun getAllAircraft() =
        repo.deleteAll()
            .thenMany(client.get()
                .retrieve()
                .bodyToFlux<Aircraft>()
                .filter { !it.reg.isNullOrEmpty() }
                .flatMap { repo.save(it) })
            .thenMany(repo.findAll())

    fun getAircraftById(id: String) = repo.findById(id)

    fun getAircraftByReg(reg: String) = repo.findAircraftByReg(reg)
}