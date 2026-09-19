package io.corkline.repository;

import io.corkline.model.TastingLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;;

@Repository
public interface TastingLogRepository extends MongoRepository<TastingLog, String> {
    List<TastingLog> findByUserId(String userId);
    List<TastingLog> findByUserIdOrderByTastingDateDesc(String userId);
    List<TastingLog> findByUserIdOrderByRatingDesc(String userId);
    List<TastingLog> findByBottleId(String bottleId);
}

