package com.thehecklers.sburrestdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@SpringBootApplication
class SburRestDemoApplication

fun main(args: Array<String>) {
    runApplication<SburRestDemoApplication>(*args)
}

@RestController
@RequestMapping("/coffees")
class RestApiDemoController {
    private val coffees: MutableList<Coffee> = ArrayList()

    init {
        coffees.addAll(
            listOf(
                Coffee("Café Cereza"),
                Coffee("Café Ganador"),
                Coffee("Café Lareño"),
                Coffee("Café Três Pontas")
            )
        )
    }

    @GetMapping
    fun getCoffees() = coffees

    @GetMapping("/{id}")
    fun getCoffeeById(@PathVariable id: String) = coffees.find { it.id == id }

    @PostMapping
    fun postCoffee(@RequestBody coffee: Coffee): Coffee {
        coffees.add(coffee)
        return coffee
    }

    @PutMapping("/{id}")
    fun putCoffee(@PathVariable id: String,
                  @RequestBody coffee: Coffee): ResponseEntity<Coffee> {
        val coffeeIndex = coffees.indexOfFirst { it.id == id }

        return if (coffeeIndex == -1) {
            ResponseEntity(postCoffee(coffee), HttpStatus.CREATED)
        } else {
            coffees[coffeeIndex] = coffee
            ResponseEntity(coffees[coffeeIndex], HttpStatus.OK)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteCoffee(@PathVariable id: String) = coffees.removeIf { c: Coffee -> c.id == id }
}

class Coffee(val name: String, val id: String = UUID.randomUUID().toString())
