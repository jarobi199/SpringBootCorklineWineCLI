package io.corkline.menu;

import io.corkline.enums.StorageType;
import io.corkline.interfaces.IMenu;
import io.corkline.service.CellarLocationService;
import io.corkline.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationMenu implements IMenu {
@Autowired
private CellarLocationService cellarLocationService;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> addCellarLocation();
            }
        }
        while (choice != 0);
    }

    public void addCellarLocation() {
        System.out.println("Add cellar description:");
        String description = InputHandler.getStringInput();
        System.out.println("Select storage type (RACK, BIN, CASE, DISPLAY):");
        StorageType storageType = StorageType.valueOf(InputHandler.getStringInput().toUpperCase());
        System.out.println("Enter the capacity:");
        int capacity = InputHandler.getIntegerInput();
        System.out.println("Enter the minimum ideal temperature (C):");
        double minIdealTemp = InputHandler.getDoubleInput();
        System.out.println("Enter the maximum ideal temperature (C):");
        double maxIdealTemp = InputHandler.getDoubleInput();
        System.out.println("Enter the minimum ideal humidity percentage:");
        double minIdealHumidity = InputHandler.getDoubleInput();
        System.out.println("Enter the maximum ideal humidity percentage:");
        double maxIdealHumidity = InputHandler.getDoubleInput();

        cellarLocationService.addCellarLocation(description, storageType, capacity, minIdealTemp, maxIdealTemp, minIdealHumidity, maxIdealHumidity);
        System.out.println("The cellar location has been successfully added!\n");
    }

    @Override
    public void printOptions() {
        System.out.println("[1] Add cellar location");
        System.out.println("[2] List all cellar locations");
        System.out.println("[3] Record condition reading");
        System.out.println("[4] View location detail");
        System.out.println("[5] Edit location");
        System.out.println("[6] Delete location");
        System.out.println("[0] Back");
        System.out.println("Please make a selection:");
    }
}
