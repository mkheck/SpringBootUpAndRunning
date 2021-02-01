package com.thehecklers.aircraftpositions;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AircraftRepository extends ReactiveCrudRepository<Aircraft, Long> {}
