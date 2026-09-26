package io.corkline.service;

import io.corkline.repository.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
    @Autowired
    private WishlistItemRepository wishlistItemRepository;


}
