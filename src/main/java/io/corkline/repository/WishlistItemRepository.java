package io.corkline.repository;

import io.corkline.enums.WishlistStatus;
import io.corkline.model.WishlistItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistItemRepository extends MongoRepository<WishlistItem, String> {
    List<WishlistItem> findByUserId(String userId);
    List<WishlistItem> findByUserIdAndStatus(String userId, WishlistStatus status);
}
