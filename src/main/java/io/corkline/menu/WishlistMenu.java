package io.corkline.menu;

import io.corkline.enums.BottleType;
import io.corkline.enums.WishlistPriority;
import io.corkline.interfaces.IMenu;
import io.corkline.service.WishlistService;
import io.corkline.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class WishlistMenu implements IMenu {
    @Autowired
    private WishlistService wishlistService;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> addWishlistItem();
                case 2 -> editWishlistItem();
                case 3 -> convertToBottle();
                case 4 -> deleteWishlistItem();
            }
        }
        while (choice != 0);
    }

    public void deleteWishlistItem() {

    }

    public void convertToBottle() {

    }

    public void editWishlistItem() {

    }

    public void addWishlistItem() {
        System.out.println("Enter the producer:");
        String producer = InputHandler.getStringInput();
        System.out.println("Enter the label:");
        String label = InputHandler.getStringInput();
        System.out.println("Select the bottle type (STILL_WINE, SPARKLING_WINE, SPIRIT):");
        BottleType bottleType = BottleType.valueOf(InputHandler.getStringInput().toUpperCase());
        System.out.println("Enter the target price:");
        double targetPrice = InputHandler.getDoubleInput();
        System.out.println("Enter the notes:");
        String notes = InputHandler.getStringInput();
        System.out.println("Enter the date added (YYYY-MM-DD):");
        LocalDate dateAdded = InputHandler.getDateInput();
        System.out.println("Select the wishlist priority (LOW, MEDIUM, HIGH):");
        WishlistPriority wishlistPriority = WishlistPriority.valueOf(InputHandler.getStringInput().toUpperCase());

        wishlistService.addWishlistItem(producer, label, bottleType, targetPrice, wishlistPriority, notes, dateAdded);
        System.out.println("Wishlist item added successfully!");

    }

    @Override
    public void printOptions() {
        System.out.println("[1] Add wishlist item");
        System.out.println("[2] Edit wishlist item");
        System.out.println("[3] Convert to bottle");
        System.out.println("[4] Delete wishlist item");
        System.out.println("[0] Back");
        System.out.println("Please make a selection:");
    }
}


