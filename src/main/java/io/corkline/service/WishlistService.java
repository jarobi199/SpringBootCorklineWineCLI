package io.corkline.service;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.BottleType;
import io.corkline.enums.WishlistPriority;
import io.corkline.model.WishlistItem;
import io.corkline.repository.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WishlistService {
    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    public void addWishlistItem(String producer, String label, BottleType bottleType, double targetPrice, WishlistPriority wishlistPriority, String notes, LocalDate dateAdded) {
        WishlistItem wishlistItem = new WishlistItem(SessionContext.getUser().getId(), producer, label, bottleType, targetPrice, wishlistPriority, notes, dateAdded);
        wishlistItemRepository.save(wishlistItem);
    }
}
