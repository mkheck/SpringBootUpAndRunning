package com.thehecklers.aircraftpositions

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PositionController(private val repository: AircraftRepository) {
    @GetMapping("/aircraft")
    fun getCurrentAircraftPositions(model: Model): String {
        model.addAttribute("currentPositions", repository.findAll())
        return "positions"
    }
}
