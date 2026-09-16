package io.corkline.menu;

import io.corkline.enums.BottleType;
import io.corkline.enums.WineBodyStyle;
import io.corkline.enums.WineColor;
import io.corkline.interfaces.IMenu;
import io.corkline.model.CellarLocation;
import io.corkline.service.BottleService;
import io.corkline.service.CellarLocationService;
import io.corkline.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BottleMenu implements IMenu {
    @Autowired
    private BottleService bottleService;
    @Autowired
    private CellarLocationService cellarLocationService;
    @Autowired
    private LocationMenu locationMenu;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> listBottles();
                case 2 -> listFavoritesAndLowStock();
                case 3 -> addBottle();
            }
        }
        while (choice != 0);
    }

    public void addBottle() {
        if(cellarLocationService.hasLocations()) {
            CellarLocation cellarLocation = locationMenu.listCellarLocationsAndSelect();
            if((cellarLocation != null) && (cellarLocationService.hasCapacity(cellarLocation))) {
                System.out.println("Select the bottle type (STILL_WINE, SPARKLING_WINE, SPIRIT):");
                BottleType bottleType = BottleType.valueOf(InputHandler.getStringInput().toUpperCase());
                System.out.println("Enter the producer:");
                String producer = InputHandler.getStringInput();
                System.out.println("Enter the label:");
                String label = InputHandler.getStringInput();
                System.out.println("Enter the vintage year:");
                String vintageYear = InputHandler.getStringInput();
                System.out.println("Enter the quantity:");
                int quantity = InputHandler.getIntegerInput();
                System.out.println("Enter the bottle size:");
                int bottleSize = InputHandler.getIntegerInput();
                System.out.println("Enter the alcohol by volume (abv):");
                int abv = InputHandler.getIntegerInput();
                System.out.println("Enter the price:");
                double price = InputHandler.getIntegerInput();
                System.out.println("Enter the purchase date (YYYY-MM-DD):");
                LocalDate purchaseDate = InputHandler.getDateInput();
                System.out.println("Is this bottle a favorite? (Y/N):");
                boolean isFavorite = InputHandler.getBooleanInput();
                System.out.println("Enter the notes:");
                String notes = InputHandler.getStringInput();

                switch (bottleType) {
                    case STILL_WINE -> {
                        System.out.println("Enter the varietal:");
                        String varietal = InputHandler.getStringInput();
                        System.out.println("Enter the region:");
                        String region = InputHandler.getStringInput();
                        System.out.println("Enter the wine color (RED, WHITE, ROSE):");
                        WineColor wineColor = WineColor.valueOf(InputHandler.getStringInput().toUpperCase());
                        System.out.println("Enter the wine body style (LIGHT, MEDIUM , FULL_BODIED):");
                        WineBodyStyle wineBodyStyle = WineBodyStyle.valueOf(InputHandler.getStringInput().toUpperCase());
                        System.out.println("Enter the aging potential years:");
                        int agingPotentialYears = InputHandler.getIntegerInput();

                        bottleService.addStillWineBottle(cellarLocation, producer, label, vintageYear, quantity, bottleSize, abv, price, purchaseDate, isFavorite, notes, varietal, region, wineColor, wineBodyStyle, agingPotentialYears);
                        System.out.println("The still wine bottle has been added successfully!");
                    }
                    case SPARKLING_WINE -> {

                        System.out.println("The sparkling wine bottle has been added successfully!");
                    }
                    case SPIRIT -> {

                        System.out.println("The spirit bottle has been added successfully!");
                    }
                }
            } else {
                System.out.println("The selected location does not have any capacity.");
            }
        }
        else
        {
            System.out.println("There is no locations in the database! You cannot add this bottle.");
        }


    }

    public void listFavoritesAndLowStock() {
        bottleService.listFavoritesAndLowStock();
    }

    public void listBottles() {
        System.out.println("Please select a filter:");
        System.out.println("[1] No filter");
        System.out.println("[2] Filter by type");
        System.out.println("[3] Filter by status");
        int filter = InputHandler.getIntegerInput();
        String value = null;

        if(filter == 2) {
            System.out.println("Please enter the type of bottle that you would like to filter on (STILL_WINE, SPARKLING_WINE, SPIRIT):");
            value = InputHandler.getStringInput();
        }
        else if(filter == 3) {
            System.out.println("Please enter the status that you would like to filter on (IN_CELLAR, CONSUMED):");
            value = InputHandler.getStringInput();
        }

        bottleService.listBottles(filter, value);
    }

    @Override
    public void printOptions() {
        System.out.println("[1] List all bottles");
        System.out.println("[2] List favorites & low stock");
        System.out.println("[3] Add bottle");
        System.out.println("[4] View bottle detail");
        System.out.println("[5] Edit bottle");
        System.out.println("[6] Move bottle");
        System.out.println("[7] Consume bottle");
        System.out.println("[8] Delete bottle");
        System.out.println("[0] Back");
        System.out.println("Please make a selection:");
    }
}
