package com.thehecklers.sburjpa

import org.springframework.data.repository.CrudRepository

interface AircraftRepository: CrudRepository<Aircraft, String>