package io.corkline.repository;

import io.corkline.model.CellarLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;;

@Repository
public interface CellarLocationRepository extends MongoRepository<CellarLocation, String> {
    List<CellarLocation> findByUserId(String userId);
}

