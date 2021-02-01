package com.thehecklers.aircraftpositions

import org.reactivestreams.Publisher
import org.springframework.http.MediaType
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveFlux
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Controller
class PositionController(
    private val service: PositionService,
    private val requester: RSocketRequester
) {

    @GetMapping("/aircraft")
    fun getCurrentAircraftPositions(model: Model): String {
        model.addAttribute("currentPositions", service.getAllAircraft())
        return "positions"
    }

    @ResponseBody
    @GetMapping("/acpos")
    fun getCurrentACPositions(): Flux<Aircraft> = service.getAllAircraft()

    @ResponseBody
    @GetMapping("/acpos/search")
    fun searchForACPosition(@RequestParam searchParams: Map<String, String>): Publisher<Aircraft?>? {

        return if (!searchParams.isEmpty()) {
            val setToSearch = searchParams.entries.iterator().next()
            if (setToSearch.key.equals("id", ignoreCase = true)) {
                service.getAircraftById(setToSearch.value)
            } else {
                service.getAircraftByReg(setToSearch.value)
            }
        } else {
            Mono.empty()
        }
    }

    @ResponseBody
    @GetMapping(value = ["/acstream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getCurrentACPositionsStream() =
        requester.route("acstream")
            .data("Requesting aircraft positions")
            .retrieveFlux<Aircraft>()
}
