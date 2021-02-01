package com.thehecklers.aircraftpositions

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.time.Instant
import java.util.*


@Component
class WebSocketHandler(private val repository: AircraftRepository) : TextWebSocketHandler() {
    private val sessionList = ArrayList<WebSocketSession>()

    fun getSessionList() = sessionList

    @Throws(Exception::class)
    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessionList.add(session)
        println("Connection established from $session @ ${Instant.now()}")
    }

    @Throws(Exception::class)
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        try {
            println("Message received: '$message', from $session")

            for (sessionInList: WebSocketSession in sessionList) {
                if (sessionInList != session) {
                    sessionInList.sendMessage(message)
                    println("--> Sending message '$message' to $sessionInList")
                }
            }
        } catch (e: Exception) {
            println("Exception handling message: ${e.localizedMessage}")
        }
    }

    @Throws(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionList.remove(session)
        println("Connection closed by $session @ ${Instant.now()}")
    }
}