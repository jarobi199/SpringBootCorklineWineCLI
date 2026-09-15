package io.corkline.repository;

import io.corkline.model.Bottle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;;

@Repository
public interface BottleRepository extends MongoRepository<Bottle, String> {
    List<Bottle> findByUserId(String userId);
    List<Bottle>  findByUserIdAndIsFavorite(String userId, boolean isFavorite);
    List<Bottle> findByLocationId(String locationId);
}

