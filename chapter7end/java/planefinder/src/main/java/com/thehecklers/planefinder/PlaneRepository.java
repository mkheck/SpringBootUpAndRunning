package com.thehecklers.planefinder;

import org.springframework.data.repository.CrudRepository;

public interface PlaneRepository extends CrudRepository<Aircraft, Long> {
}
