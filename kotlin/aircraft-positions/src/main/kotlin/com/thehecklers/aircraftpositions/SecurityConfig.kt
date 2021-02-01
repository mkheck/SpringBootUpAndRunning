package com.thehecklers.aircraftpositions

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.provisioning.InMemoryUserDetailsManager


@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    private val pwEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun authentication(): UserDetailsService {
        val peter = User.builder()
            .username("peter")
            .password(pwEncoder.encode("ppassword"))
            .roles("USER")
            .build()

        val jodie = User.builder()
            .username("jodie")
            .password(pwEncoder.encode("jpassword"))
            .roles("USER", "ADMIN")
            .build()

        println("   >>> Peter's password: " + peter.password)
        println("   >>> Jodie's password: " + jodie.password)

        return InMemoryUserDetailsManager(peter, jodie)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .mvcMatchers("/aircraftadmin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .and()
            .httpBasic()
    }
}