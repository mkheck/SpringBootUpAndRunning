package com.thehecklers.aircraftpositions

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.TextMessage
import java.io.IOException
import java.util.function.Consumer


@Configuration
class PositionRetriever(
    private val repository: AircraftRepository,
    private val handler: WebSocketHandler
) {
    @Bean
    fun retrieveAircraftPositions() = Consumer<List<Aircraft>> {
        repository.deleteAll()

        repository.saveAll(it)

        sendPositions()
    }

    private fun sendPositions() {
        if (repository.count() > 0) {
            for (sessionInList in handler.getSessionList()) {
                try {
                    sessionInList.sendMessage(
                        TextMessage(repository.findAll().toString())
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
