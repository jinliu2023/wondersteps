package com.example.wonder_steps_generator.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataWonderStepRepository extends MongoRepository<WonderStepDocument, String> {

	List<WonderStepDocument> findByTripId(String tripId, Sort sort);

	List<WonderStepDocument> findByTripIdIn(List<String> tripIds, Sort sort);
}
