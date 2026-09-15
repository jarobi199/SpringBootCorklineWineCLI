package io.corkline.menu;

import io.corkline.enums.StorageType;
import io.corkline.interfaces.IMenu;
import io.corkline.model.CellarLocation;
import io.corkline.service.CellarLocationService;
import io.corkline.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
                case 2 -> addCellarLocation();
                case 3 -> recordConditionReading();
            }
        }
        while (choice != 0);
    }

    public void recordConditionReading() {
        CellarLocation cellarLocation = listCellarLocationsAndSelect();
        if (cellarLocation != null) {
            System.out.println("Enter the current temperature (C):");
            int currentTemp = InputHandler.getIntegerInput();
            System.out.println("Enter the humidity percentage:");
            int humidity = InputHandler.getIntegerInput();
            System.out.println("Enter a note:");
            String note = InputHandler.getStringInput();

            cellarLocationService.recordConditionReading(cellarLocation, currentTemp, humidity, note);
            System.out.println("The condition reading has been successfully recorded!\n");
        }
    }

    public void addCellarLocation() {
        System.out.println("Add cellar description:");
        String description = InputHandler.getStringInput();
        System.out.println("Select storage type (RACK, BIN, CASE, DISPLAY):");
        StorageType storageType = StorageType.valueOf(InputHandler.getStringInput().toUpperCase());
        System.out.println("Enter the capacity:");
        int capacity = InputHandler.getIntegerInput();
        System.out.println("Enter the minimum ideal temperature (C):");
        int minIdealTemp = InputHandler.getIntegerInput();
        System.out.println("Enter the maximum ideal temperature (C):");
        int maxIdealTemp = InputHandler.getIntegerInput();
        System.out.println("Enter the minimum ideal humidity percentage:");
        int minIdealHumidity = InputHandler.getIntegerInput();
        System.out.println("Enter the maximum ideal humidity percentage:");
        int maxIdealHumidity = InputHandler.getIntegerInput();

        cellarLocationService.addCellarLocation(description, storageType, capacity, minIdealTemp, maxIdealTemp, minIdealHumidity, maxIdealHumidity);
        System.out.println("The cellar location has been successfully added!\n");
    }

    private CellarLocation listCellarLocationsAndSelect() {
        int number = 1;
        CellarLocation cellarLocation = null;
        int choice = 0;

        List<CellarLocation> cellarLocations = cellarLocationService.getCellarLocations();

        if(!cellarLocations.isEmpty()) {
            for (CellarLocation c : cellarLocations) {
                System.out.println("[" + number + "] " +  c.getName() + " (Capacity: " + c.getCapacity()+ ")");
                number++;
            }
            System.out.println("Select a cellar location:");
            choice = InputHandler.getIntegerInput();
            cellarLocation = cellarLocations.get(choice - 1);
        }
        else
        {
            System.out.println("There are no cellar locations available.");
        }

        return cellarLocation;
    }

    @Override
    public void printOptions() {
        System.out.println("[1] List all cellar locations");
        System.out.println("[2] Add cellar location");
        System.out.println("[3] Record condition reading");
        System.out.println("[4] View location detail");
        System.out.println("[5] Edit location");
        System.out.println("[6] Delete location");
        System.out.println("[0] Back");
        System.out.println("Please make a selection:");
    }
}
