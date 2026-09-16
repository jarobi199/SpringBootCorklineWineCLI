package io.corkline.menu;

import io.corkline.interfaces.IMenu;
import io.corkline.service.BottleService;
import io.corkline.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BottleMenu implements IMenu {
    @Autowired
    private BottleService bottleService;

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
        /*System.out.println("Select the species type (RESIDENTIAL, COMMERCIAL, VACATION_RENTAL):");
        PropertyType propertyType = PropertyType.valueOf(InputHandler.getStringInput().toUpperCase());
        System.out.println("Enter the address of the property:");
        String address = InputHandler.getStringInput();*/
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
