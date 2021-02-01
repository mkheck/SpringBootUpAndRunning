package com.thehecklers.aircraftpositions

//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.mockito.Mockito
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.context.ApplicationContext
//import org.springframework.test.web.reactive.server.WebTestClient
//import org.springframework.test.web.reactive.server.expectBodyList
//import reactor.core.publisher.Flux
//import java.time.Instant
//
//@WebFluxTest(controllers = [PositionController::class])
//internal class PositionControllerTest {
//    @MockBean
//    private lateinit var repository: AircraftRepository
//
//    private lateinit var ac1: Aircraft
//    private lateinit var ac2: Aircraft
//
//    @BeforeEach
//    fun setUp(context: ApplicationContext?) {
//        // Spring Airlines flight 001 en route, flying STL to SFO,
//        //    at 30000' currently over Kansas City
//        ac1 = Aircraft(
//            "1", "SAL001", "sqwk", "N12345", "SAL001",
//            "STL-SFO", "LJ", "ct",
//            30000, 280, 440, 0, 0,
//            39.2979849, -94.71921, 0.0, 0.0, 0.0,
//            true, false,
//            Instant.now(), Instant.now(), Instant.now()
//        )
//
//        // Spring Airlines flight 002 en route, flying SFO to STL,
//        //    at 40000' currently over Denver
//        ac2 = Aircraft(
//            "2", "SAL002", "sqwk", "N54321", "SAL002",
//            "SFO-STL", "LJ", "ct",
//            40000, 65, 440, 0, 0,
//            39.8560963, -104.6759263, 0.0, 0.0, 0.0,
//            true, false,
//            Instant.now(), Instant.now(), Instant.now()
//        )
//        Mockito.`when`(repository.findAll())
//            .thenReturn(Flux.just(ac1, ac2))
//    }
//
//    @Test // NOTE: This test fails prior to refactoring
//    fun getCurrentAircraftPositions(@Autowired client: WebTestClient) {
//        val acPositions: Iterable<Aircraft> = client.get()
//            .uri("/aircraft")
//            .exchange()
//            .expectStatus().isOk
//            .expectBodyList<Aircraft>()
//            .returnResult()
//            .responseBody!!
//
//        Assertions.assertEquals(listOf(ac1, ac2), acPositions)
//    }
//}

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Hooks
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import reactor.tools.agent.ReactorDebugAgent
import java.time.Instant


@WebFluxTest(controllers = [PositionController::class])
internal class PositionControllerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @MockBean
    private lateinit var service: PositionService

    @MockBean
    private lateinit var requester: RSocketRequester

    private lateinit var ac1: Aircraft
    private lateinit var ac2: Aircraft
    private lateinit var ac3: Aircraft

    @BeforeEach
    fun setUp() {
        // Spring Airlines flight 001 en route, flying STL to SFO, at 30000'
        // currently over Kansas City
        ac1 = Aircraft(
            "1", "SAL001", "sqwk", "N12345", "SAL001",
            "STL-SFO", "LJ", "ct",
            30000, 280, 440, 0, 0,
            39.2979849, -94.71921, 0.0, 0.0, 0.0,
            false, false,
            Instant.now(), Instant.now(), Instant.now()
        )

        // Spring Airlines flight 002 en route, flying SFO to STL, at 40000'
        // currently over Denver
        ac2 = Aircraft(
            "2", "SAL002", "sqwk", "N54321", "SAL002",
            "SFO-STL", "LJ", "ct",
            40000, 65, 440, 0, 0,
            39.8560963, -104.6759263, 0.0, 0.0, 0.0,
            false, false,
            Instant.now(), Instant.now(), Instant.now()
        )

        // Spring Airlines flight 002 en route, flying SFO to STL, at 40000'
        // currently just past DEN
        ac3 = Aircraft(
            "3", "SAL002", "sqwk", "N54321", "SAL002",
            "SFO-STL", "LJ", "ct",
            40000, 65, 440, 0, 0,
            39.8412964, -105.0048267, 0.0, 0.0, 0.0,
            false, false,
            Instant.now(), Instant.now(), Instant.now()
        )

        // Replace this line in turn with each section below, then run test getCurrentACPositions()
        Mockito.`when`(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3))

        //Hooks.onOperatorDebug()
        //Mockito.`when`(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .concatWith(Flux.error(Throwable("Bad position report"))))

        //Mockito.`when`(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .checkpoint()
        //        .concatWith(Flux.error(Throwable("Bad position report")))
        //        .checkpoint())

        //Mockito.`when`(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .checkpoint("All Aircraft: after all good positions reported")
        //        .concatWith(Flux.error(Throwable("Bad position report")))
        //        .checkpoint("All Aircraft: after appending bad position report"))

        //Mockito.`when`(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .checkpoint("All Aircraft: after all good positions reported", true)
        //        .concatWith(Flux.error(Throwable("Bad position report")))
        //        .checkpoint("All Aircraft: after appending bad position report", true))

        //ReactorDebugAgent.init()
        //Mockito.`when`(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .concatWith(Flux.error(Throwable("Bad position report"))))

        Mockito.`when`(service.getAircraftById("1")).thenReturn(Mono.just(ac1))
        Mockito.`when`(service.getAircraftById("2")).thenReturn(Mono.just(ac2))
        Mockito.`when`(service.getAircraftById("3")).thenReturn(Mono.just(ac3))
        Mockito.`when`(service.getAircraftByReg("N12345")).thenReturn(Flux.just(ac1))
        Mockito.`when`(service.getAircraftByReg("N54321")).thenReturn(Flux.just(ac2, ac3))
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun currentACPositions() {
        StepVerifier.create(
            client.get()
                .uri("/acpos")
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult<Aircraft>()
                .responseBody
        )
            .expectNext(ac1)
            .expectNext(ac2)
            .expectNext(ac3)
            .verifyComplete()
    }

    @Test
    fun searchForACPositionById() {
        StepVerifier.create(
            client.get()
                .uri("/acpos/search?id=1")
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult<Aircraft>()
                .responseBody
        )
            .expectNext(ac1)
            .verifyComplete()
    }

    @Test
    fun searchForACPositionByReg() {
        StepVerifier.create(
            client.get()
                .uri("/acpos/search?reg=N54321")
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult<Aircraft>()
                .responseBody
        )
            .expectNext(ac2)
            .expectNext(ac3)
            .verifyComplete()
    }
}
