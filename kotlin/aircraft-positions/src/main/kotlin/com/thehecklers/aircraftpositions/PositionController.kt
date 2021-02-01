package com.thehecklers.aircraftpositions

import org.springframework.http.MediaType
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveFlux
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux


@Controller
class PositionController(
    private val repository: AircraftRepository,
    builder: RSocketRequester.Builder
) {
    private val client = WebClient.create("http://localhost:7634/aircraft")
    private val requester = builder.tcp("localhost", 7635)

    // HTTP endpoint, HTTP requester (previously created)
    @GetMapping("/aircraft")
    fun getCurrentAircraftPositions(model: Model): String {
        val aircraftFlux = repository.deleteAll()
            .thenMany(client.get()
                .retrieve()
                .bodyToFlux<Aircraft>()
                .filter { !it.reg.isNullOrEmpty() }
                .flatMap { repository.save(it) })

        model.addAttribute("currentPositions", aircraftFlux)
        return "positions"
    }

    // HTTP endpoint, RSocket client endpoint
    @ResponseBody
    @GetMapping(value = ["/acstream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getCurrentACPositionsStream() =
        requester.route("acstream")
            .data("Requesting aircraft positions")
            .retrieveFlux<Aircraft>()
}
