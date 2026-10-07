package io.corkline.menu;

import io.corkline.enums.*;
import io.corkline.interfaces.IMenu;
import io.corkline.model.CellarLocation;
import io.corkline.model.WishlistItem;
import io.corkline.service.BottleService;
import io.corkline.service.CellarLocationService;
import io.corkline.service.WishlistService;
import io.corkline.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class WishlistMenu implements IMenu {
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private CellarLocationService cellarLocationService;
    @Autowired
    private BottleService bottleService;
    @Autowired
    private LocationMenu locationMenu;

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
        WishlistItem wishlistItem = listWishlistItemsAndSelect(null);
        if (wishlistItem != null) {
            wishlistService.deleteWishlistItem(wishlistItem);
            System.out.println("Wishlist item successfully deleted!");
        }
    }

    public void convertToBottle() {
        WishlistItem wishlistItem = listWishlistItemsAndSelect(WishlistStatus.WANTED);
        if (wishlistItem != null) {
            if(cellarLocationService.hasLocations()) {
                CellarLocation cellarLocation = locationMenu.listCellarLocationsAndSelect();
                if((cellarLocation != null) && (cellarLocationService.hasCapacity(cellarLocation))) {
                    System.out.println("Enter the vintage year:");
                    int vintageYear = InputHandler.getIntegerInput();
                    System.out.println("Enter the quantity:");
                    int quantity = InputHandler.getIntegerInput();
                    System.out.println("Enter the bottle size:");
                    int bottleSize = InputHandler.getIntegerInput();
                    System.out.println("Enter the alcohol by volume (abv):");
                    double abv = InputHandler.getDoubleInput();
                    System.out.println("Enter the purchase date (YYYY-MM-DD):");
                    LocalDate purchaseDate = InputHandler.getDateInput();
                    System.out.println("Is this bottle a favorite? (Y/N):");
                    boolean isFavorite = InputHandler.getBooleanInput();

                    switch (wishlistItem.getBottleType()) {
                        case STILL_WINE -> {
                            System.out.println("Enter the varietal:");
                            String varietal = InputHandler.getStringInput();
                            System.out.println("Enter the region:");
                            String region = InputHandler.getStringInput();
                            System.out.println("Enter the wine color (RED, WHITE, ROSE):");
                            WineColor wineColor = WineColor.valueOf(InputHandler.getStringInput().toUpperCase());
                            System.out.println("Enter the wine body style (LIGHT, MEDIUM, FULL_BODIED):");
                            WineBodyStyle wineBodyStyle = WineBodyStyle.valueOf(InputHandler.getStringInput().toUpperCase());
                            System.out.println("Enter the aging potential years:");
                            int agingPotentialYears = InputHandler.getIntegerInput();

                            bottleService.addStillWineBottle(cellarLocation, wishlistItem.getProducer(), wishlistItem.getLabel(), vintageYear, quantity, bottleSize, abv, wishlistItem.getTargetPrice(), purchaseDate, isFavorite, wishlistItem.getNotes(), varietal, region, wineColor, wineBodyStyle, agingPotentialYears);
                            System.out.println("The still wine bottle has been added successfully!");
                        }
                        case SPARKLING_WINE -> {
                            System.out.println("Select the dosage level (BRUT_NATURE, EXTRA_BRUT, BRUT, SEC, DEMI_SEC):");
                            DosageLevel dosageLevel = DosageLevel.valueOf(InputHandler.getStringInput().toUpperCase());
                            System.out.println("Select the production method (TRADITIONAL, CHARMAT):");
                            ProductionMethod productionMethod = ProductionMethod.valueOf(InputHandler.getStringInput().toUpperCase());
                            System.out.println("Is this wine vintage? (Y/N):");
                            boolean isVintage = InputHandler.getBooleanInput();

                            bottleService.addSparklingWineBottle(cellarLocation, wishlistItem.getProducer(), wishlistItem.getLabel(), vintageYear, quantity, bottleSize, abv, wishlistItem.getTargetPrice(), purchaseDate, isFavorite, wishlistItem.getNotes(), dosageLevel, productionMethod, isVintage);
                            System.out.println("The sparkling wine bottle has been added successfully!");
                        }
                        case SPIRIT -> {
                            System.out.println("Select the spirit type (WHISKEY, BRANDY, RUM, GIN, TEQUILA, OTHER):");
                            SpiritType spiritType = SpiritType.valueOf(InputHandler.getStringInput().toUpperCase());
                            System.out.println("Enter the distillation year:");
                            int distillationYear = InputHandler.getIntegerInput();
                            System.out.println("Is this cask strength? (Y/N):");
                            boolean caskStrength = InputHandler.getBooleanInput();
                            System.out.println("Enter the number of aged years:");
                            int agedYears = InputHandler.getIntegerInput();

                            bottleService.addSpiritBottle(cellarLocation, wishlistItem.getProducer(), wishlistItem.getLabel(), vintageYear, quantity, bottleSize, abv, wishlistItem.getTargetPrice(), purchaseDate, isFavorite, wishlistItem.getNotes(), spiritType, distillationYear, caskStrength, agedYears);
                            System.out.println("The spirit bottle has been added successfully!");
                        }
                    }
                    wishlistService.convertToBottle(wishlistItem);
                }
                else
                {
                    System.out.println("The selected location does not have any capacity.");
                }
            }
            else
            {
                System.out.println("There is no locations in the database! You cannot convert this bottle.");
            }
        }
    }

    public void editWishlistItem() {
        WishlistItem wishlistItem = listWishlistItemsAndSelect(null);
        if (wishlistItem != null) {
            System.out.println("Enter the new target price:");
            double targetPrice = InputHandler.getDoubleInput();
            System.out.println("Select the new wishlist priority (LOW, MEDIUM, HIGH):");
            WishlistPriority wishlistPriority = WishlistPriority.valueOf(InputHandler.getStringInput().toUpperCase());
            System.out.println("Enter the new notes:");
            String notes = InputHandler.getStringInput();

            wishlistService.editWishlistItem(wishlistItem, targetPrice, wishlistPriority, notes);
        }
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

    public WishlistItem listWishlistItemsAndSelect(WishlistStatus wishlistStatus) {
        int number = 1;
        WishlistItem wishlistItem = null;
        int choice;
        List<WishlistItem> wishlistItems = null;

        if (wishlistStatus != null) {
            wishlistItems = wishlistService.getAllWishlistItems().stream().filter(w -> w.getStatus().equals(wishlistStatus)).toList();
        }
        else
        {
            wishlistItems = wishlistService.getAllWishlistItems();
        }

        if (!wishlistItems.isEmpty()) {
            for (WishlistItem w : wishlistItems) {
                System.out.println("[" + number + "] " + w.getProducer() + " - " + w.getLabel() + " (" + w.getBottleType() + ")");
                number++;
            }
            System.out.println("Select a wishlist item:");
            choice = InputHandler.getIntegerInput();
            wishlistItem = wishlistItems.get(choice - 1);
        } else {
            System.out.println("There are no wishlist items available.");
        }

        return wishlistItem;
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


