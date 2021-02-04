package com.thehecklers.sburrestdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.bind.Name
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.CrudRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.annotation.PostConstruct
import javax.persistence.Entity
import javax.persistence.Id


@SpringBootApplication
@ConfigurationPropertiesScan
class SburRestDemoApplication {
    @Bean
    @ConfigurationProperties(prefix = "droid")
    fun createDroid() = Droid()
}

fun main(args: Array<String>) {
    runApplication<SburRestDemoApplication>(*args)
}

@Component
class DataLoader(private val coffeeRepository: CoffeeRepository) {
    @PostConstruct
    private fun loadData() = coffeeRepository.saveAll(
        listOf(
            Coffee("Café Cereza"),
            Coffee("Café Ganador"),
            Coffee("Café Lareño"),
            Coffee("Café Três Pontas")
        )
    )
}

@RestController
@RequestMapping("/droid")
class DroidController(@get:GetMapping val droid: Droid)

@RestController
@RequestMapping("/greeting")
class GreetingController(private val greeting: Greeting) {
    @GetMapping
    fun getGreeting() = greeting.name

    @GetMapping("/coffee")
    fun getNameAndCoffee() = greeting.coffee
}

@RestController
@RequestMapping("/coffees")
class RestApiDemoController(private val coffeeRepository: CoffeeRepository) {
    @GetMapping
    fun getCoffees() = coffeeRepository.findAll()

    @GetMapping("/{id}")
    fun getCoffeeById(@PathVariable id: String) = coffeeRepository.findById(id)

    @PostMapping
    fun postCoffee(@RequestBody coffee: Coffee) = coffeeRepository.save(coffee)

    @PutMapping("/{id}")
    fun putCoffee(
        @PathVariable id: String,
        @RequestBody coffee: Coffee
    ): ResponseEntity<Coffee> {
        return if (coffeeRepository.existsById(id))
            ResponseEntity(coffeeRepository.save(coffee), HttpStatus.OK)
        else
            ResponseEntity(coffeeRepository.save(coffee), HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteCoffee(@PathVariable id: String) = coffeeRepository.deleteById(id)
}

class Droid {
    var id: String? = null
    var description: String? = null
}

@ConfigurationProperties(prefix = "greeting")
@ConstructorBinding
class Greeting(
    val name: String,
    val coffee: String
)

interface CoffeeRepository : CrudRepository<Coffee, String>

@Entity
class Coffee(val name: String = "Cup O' Joe", @Id val id: String = UUID.randomUUID().toString())
