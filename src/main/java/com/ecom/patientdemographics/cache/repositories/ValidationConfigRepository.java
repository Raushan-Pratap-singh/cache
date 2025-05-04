package com.ecom.patientdemographics.cache.repositories;

import com.ecom.patientdemographics.cache.models.ValidationConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationConfigRepository extends MongoRepository<ValidationConfig, String> {
}