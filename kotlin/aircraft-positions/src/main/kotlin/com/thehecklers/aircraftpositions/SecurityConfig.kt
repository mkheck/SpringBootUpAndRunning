package com.thehecklers.aircraftpositions

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class SecurityConfig {
    @Bean
    fun client(regRepo: ClientRegistrationRepository, cliRepo: OAuth2AuthorizedClientRepository): WebClient {
        val filter = ServletOAuth2AuthorizedClientExchangeFilterFunction(regRepo, cliRepo)

        filter.setDefaultOAuth2AuthorizedClient(true)

        return WebClient.builder()
            .baseUrl("http://localhost:7634/")
            .apply(filter.oauth2Configuration())
            .build()
    }
}