package com.thehecklers.aircraftpositions

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.rsocket.RSocketRequester

@Configuration
class RSocketRequesterConfig {
    @Bean
    fun requester(builder: RSocketRequester.Builder): RSocketRequester {
        return builder.tcp("localhost", 7635)
    }
}
