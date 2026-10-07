package io.corkline.service;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.BottleType;
import io.corkline.enums.WishlistPriority;
import io.corkline.enums.WishlistStatus;
import io.corkline.model.WishlistItem;
import io.corkline.repository.WishlistItemRepository;
import io.corkline.util.InputHandler;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WishlistService {
    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    public void addWishlistItem(String producer, String label, BottleType bottleType, double targetPrice, WishlistPriority wishlistPriority, String notes, LocalDate dateAdded) {
        WishlistItem wishlistItem = new WishlistItem(SessionContext.getUser().getId(), producer, label, bottleType, targetPrice, wishlistPriority, notes, dateAdded);
        wishlistItemRepository.save(wishlistItem);
    }

    public List<WishlistItem> getAllWishlistItems() {
        return wishlistItemRepository.findByUserId(SessionContext.getUser().getId());
    }

    public void editWishlistItem(WishlistItem wishListItem, double price, WishlistPriority wishlistPriority, String notes) {
        wishListItem.setTargetPrice(price);
        wishListItem.setPriority(wishlistPriority);
        wishListItem.setNotes(notes);
        wishlistItemRepository.save(wishListItem);
    }

    public void deleteWishlistItem(WishlistItem wishlistItem) {
        wishlistItemRepository.delete(wishlistItem);
    }

    public void convertToBottle(WishlistItem wishlistItem) {
        wishlistItem.setStatus(WishlistStatus.ACQUIRED);
        wishlistItemRepository.save(wishlistItem);
    }

    public void displayWishlistItems() {
        List<WishlistItem> wishlistItems = wishlistItemRepository.findByUserId(SessionContext.getUser().getId());
        if(wishlistItems.isEmpty()){
            System.out.println("There are no wishlist items found.");
        }
        else
        {
            System.out.println("| WISHLIST ITEMS |");
            Table wishlistTable = Clique.table(TableType.BOX_DRAW)
                    .headers(
                            "[yellow, bold]PRODUCER[/]",
                            "[yellow, bold]LABEL[/]",
                            "[yellow, bold]BOTTLE TYPE[/]",
                            "[yellow, bold]TARGET PRICE[/]",
                            "[yellow, bold]PRIORITY[/]",
                            "[yellow, bold]NOTES[/]",
                            "[yellow, bold]DATE ADDED[/]",
                            "[yellow, bold]STATUS[/]"
                    );
            for (WishlistItem wishlistItem : wishlistItems) {
                wishlistTable.row(wishlistItem.getProducer(), wishlistItem.getLabel(), wishlistItem.getBottleType().name(), InputHandler.formatAsMoney(wishlistItem.getTargetPrice()), wishlistItem.getPriority().name(),
                        wishlistItem.getNotes(), wishlistItem.getDateAdded().toString(), wishlistItem.getStatus().name());
            }
            wishlistTable.render();
        }
    }
}
