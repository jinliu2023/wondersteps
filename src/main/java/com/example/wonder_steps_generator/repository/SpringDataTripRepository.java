package com.example.wonder_steps_generator.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataTripRepository extends MongoRepository<TripDocument, String> {

	List<TripDocument> findByStatusAndFeatured(String status, boolean featured, Sort sort);

	List<TripDocument> findByStatus(String status, Sort sort);
}
