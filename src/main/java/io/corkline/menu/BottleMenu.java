package io.corkline.menu;

import io.corkline.enums.*;
import io.corkline.interfaces.IMenu;
import io.corkline.model.Bottle;
import io.corkline.model.CellarLocation;
import io.corkline.service.BottleService;
import io.corkline.service.CellarLocationService;
import io.corkline.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

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
                case 4 -> viewBottleDetails();
                case 5 -> editBottle();
                case 6 -> moveBottle();
                case 7 -> consumeBottle();
            }
        }
        while (choice != 0);
    }

    public void viewBottleDetails() {
        Bottle bottle = listBottlesAndSelect();
        if (bottle != null) {
            System.out.println("| BOTTLE DETAILS |");
            System.out.println(bottle.getDetails());
            //TODO: Add tasting log code
        }
    }

    public void editBottle() {
        Bottle bottle = listBottlesAndSelect();
        if (bottle != null) {
            System.out.println("Enter the new quantity:");
            int quantity = InputHandler.getIntegerInput();
            System.out.println("Is this bottle a favorite? (Y/N):");
            boolean isFavorite = InputHandler.getBooleanInput();
            System.out.println("Enter the new notes:");
            String notes = InputHandler.getStringInput();

            switch (bottle.getBottleType()) {
                case STILL_WINE -> {
                    System.out.println("Enter the new aging potential years:");
                    int agingPotentialYears = InputHandler.getIntegerInput();

                    bottleService.editStillWineBottle(bottle, quantity, isFavorite, notes, agingPotentialYears);
                    System.out.println("The still wine bottle has been updated successfully!");
                }
                case SPARKLING_WINE -> {
                    System.out.println("Select the dosage level (BRUT_NATURE, EXTRA_BRUT, BRUT, SEC , DEMI_SEC):");
                    DosageLevel dosageLevel = DosageLevel.valueOf(InputHandler.getStringInput().toUpperCase());

                    bottleService.editSparklingWineBottle(bottle, quantity, isFavorite, notes, dosageLevel);
                    System.out.println("The sparkling wine bottle has been updated successfully!");
                }
                case SPIRIT -> {
                    System.out.println("Is this cask strength? (Y/N):");
                    boolean caskStrength = InputHandler.getBooleanInput();

                    bottleService.editSpiritBottle(bottle, quantity, isFavorite, notes, caskStrength);
                    System.out.println("The spirit bottle has been updated successfully!");
                }
            }
        }
    }


    public void consumeBottle() {

        Bottle bottle = listBottlesAndSelect();
        System.out.println("Enter the number of bottles you want to consume:");
        int amount = InputHandler.getIntegerInput();

        bottleService.consumeBottles(bottle, amount);
        System.out.println("You have consumed " +  amount + " bottle(s)!");
    }

    public void moveBottle() {
        Bottle bottle = listBottlesAndSelect();
        if (cellarLocationService.hasLocations()) {
            CellarLocation cellarLocation = locationMenu.listCellarLocationsAndSelect();
            if ((cellarLocation != null) && (cellarLocationService.hasCapacity(cellarLocation))) {
                bottleService.moveBottle(cellarLocation, bottle);
            }
            else
            {
                System.out.println("The bottle cannot be moved because the selected location does not have any capacity.");
            }
        }
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
                        System.out.println("Select the dosage level (BRUT_NATURE, EXTRA_BRUT, BRUT, SEC , DEMI_SEC):");
                        DosageLevel dosageLevel = DosageLevel.valueOf(InputHandler.getStringInput().toUpperCase());
                        System.out.println("Select the production method (TRADITIONAL, CHARMAT):");
                        ProductionMethod productionMethod = ProductionMethod.valueOf(InputHandler.getStringInput().toUpperCase());
                        System.out.println("Is this wine vintage? (Y/N):");
                        boolean isVintage = InputHandler.getBooleanInput();

                        bottleService.addSparklingWineBottle(cellarLocation, producer, label, vintageYear, quantity, bottleSize, abv, price, purchaseDate, isFavorite, notes, dosageLevel, productionMethod, isVintage);
                        System.out.println("The sparkling wine bottle has been added successfully!");
                    }
                    case SPIRIT -> {
                        System.out.println("Select the spirit type (WHISKEY, BRANDY, RUM, GIN, TEQUILA, OTHER):");
                        SpiritType spiritType = SpiritType.valueOf(InputHandler.getStringInput().toUpperCase());
                        System.out.println("Enter the distillation year:");
                        String distillationYear = InputHandler.getStringInput();
                        System.out.println("Is this cask strength? (Y/N):");
                        boolean caskStrength = InputHandler.getBooleanInput();
                        System.out.println("Enter the number of aged years:");
                        int agedYears = InputHandler.getIntegerInput();

                        bottleService.addSpiritBottle(cellarLocation, producer, label, vintageYear, quantity, bottleSize, abv, price, purchaseDate, isFavorite, notes, spiritType, distillationYear, caskStrength, agedYears);
                        System.out.println("The spirit bottle has been added successfully!");
                    }
                }
            }
            else
            {
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

    private Bottle listBottlesAndSelect() {
        int number = 1;
        Bottle bottle = null;
        int choice;

        List<Bottle> bottles = bottleService.getAllBottles();

        if(!bottles.isEmpty()) {
            for (Bottle b : bottles) {
                System.out.println("[" + number + "] " +  b.getProducer() + " - " + b.getLabel());
                number++;
            }
            System.out.println("Select a bottle:");
            choice = InputHandler.getIntegerInput();
            bottle = bottles.get(choice - 1);
        }
        else
        {
            System.out.println("There are no bottles available.");
        }

        return bottle;
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
