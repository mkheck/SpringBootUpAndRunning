package com.thehecklers.sburrestdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
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
class SburRestDemoApplication

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

interface CoffeeRepository : CrudRepository<Coffee, String>

@Entity
class Coffee(val name: String = "Cup O' Joe", @Id val id: String = UUID.randomUUID().toString())
