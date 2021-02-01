package com.thehecklers.aircraftpositions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.tools.agent.ReactorDebugAgent;

import java.time.Instant;

@WebFluxTest(controllers = {PositionController.class})
class PositionControllerTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private PositionService service;
    @MockBean
    private RSocketRequester requester;

    private Aircraft ac1, ac2, ac3;

    @BeforeEach
    void setUp() {
        // Spring Airlines flight 001 en route, flying STL to SFO, at 30000'
        // currently over Kansas City
        ac1 = new Aircraft(1L, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SFO", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        // Spring Airlines flight 002 en route, flying SFO to STL, at 40000'
        // currently over Denver
        ac2 = new Aircraft(2L, "SAL002", "sqwk", "N54321", "SAL002",
                "SFO-STL", "LJ", "ct",
                40000, 65, 440, 0, 0,
                39.8560963, -104.6759263, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        // Spring Airlines flight 002 en route, flying SFO to STL, at 40000'
        // currently just past DEN
        ac3 = new Aircraft(3L, "SAL002", "sqwk", "N54321", "SAL002",
                "SFO-STL", "LJ", "ct",
                40000, 65, 440, 0, 0,
                39.8412964, -105.0048267, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        // Replace this line in turn with each section below, then run test getCurrentACPositions()
        Mockito.when(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3));

        //Hooks.onOperatorDebug();
        //Mockito.when(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .concatWith(Flux.error(new Throwable("Bad position report"))));

        //Mockito.when(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .checkpoint()
        //        .concatWith(Flux.error(new Throwable("Bad position report")))
        //        .checkpoint());

        //Mockito.when(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .checkpoint("All Aircraft: after all good positions reported")
        //        .concatWith(Flux.error(new Throwable("Bad position report")))
        //        .checkpoint("All Aircraft: after appending bad position report"));

        //Mockito.when(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .checkpoint("All Aircraft: after all good positions reported", true)
        //        .concatWith(Flux.error(new Throwable("Bad position report")))
        //        .checkpoint("All Aircraft: after appending bad position report", true));

        //ReactorDebugAgent.init();
        //Mockito.when(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .concatWith(Flux.error(new Throwable("Bad position report"))));

        Mockito.when(service.getAircraftById(1L)).thenReturn(Mono.just(ac1));
        Mockito.when(service.getAircraftById(2L)).thenReturn(Mono.just(ac2));
        Mockito.when(service.getAircraftById(3L)).thenReturn(Mono.just(ac3));
        Mockito.when(service.getAircraftByReg("N12345")).thenReturn(Flux.just(ac1));
        Mockito.when(service.getAircraftByReg("N54321")).thenReturn(Flux.just(ac2, ac3));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCurrentACPositions() {
        StepVerifier.create(client.get()
                .uri("/acpos")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Aircraft.class)
                .getResponseBody())
                .expectNext(ac1)
                .expectNext(ac2)
                .expectNext(ac3)
                .verifyComplete();
    }

    @Test
    void searchForACPositionById() {
        StepVerifier.create(client.get()
                .uri("/acpos/search?id=1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Aircraft.class)
                .getResponseBody())
                .expectNext(ac1)
                .verifyComplete();
    }

    @Test
    void searchForACPositionByReg() {
        StepVerifier.create(client.get()
                .uri("/acpos/search?reg=N54321")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Aircraft.class)
                .getResponseBody())
                .expectNext(ac2)
                .expectNext(ac3)
                .verifyComplete();
    }
}
