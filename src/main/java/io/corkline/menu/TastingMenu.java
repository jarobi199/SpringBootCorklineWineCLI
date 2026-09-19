package io.corkline.menu;

import io.corkline.interfaces.IMenu;
import io.corkline.model.Bottle;
import io.corkline.service.TastingLogService;
import io.corkline.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TastingMenu implements IMenu {
    @Autowired
    private BottleMenu bottleMenu;
    @Autowired
    private TastingLogService tastingLogService;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> logTasting();
                case 2 -> viewHistoryByBottle();
                case 3 -> viewAllTastings();
            }
        }
        while (choice != 0);
    }

    public void viewAllTastings() {
        tastingLogService.viewAllTastingLogs();
    }

    public void viewHistoryByBottle() {
        Bottle bottle = bottleMenu.listBottlesAndSelect();
        if (bottle != null) {
            tastingLogService.viewHistoryByBottle(bottle);
        }
    }

    public void logTasting() {
        Bottle bottle = bottleMenu.listBottlesAndSelect();
        if (bottle != null) {
            System.out.println("Enter the tasting date (YYYY-MM-DD):");
            LocalDate tastingDate = InputHandler.getDateInput();
            System.out.println("Enter the rating (0-100):");
            int rating = InputHandler.getIntegerInput();
            System.out.println("Enter the notes:");
            String notes = InputHandler.getStringInput();
            System.out.println("Enter the occasion:");
            String occasion = InputHandler.getStringInput();
            System.out.println("Enter the quantity consumed:");
            int consumed = InputHandler.getIntegerInput();

            tastingLogService.logTasting(bottle, tastingDate, rating, notes,occasion, consumed);
            System.out.println("The tasting log has been logged successfully!");
        }
    }

    @Override
    public void printOptions() {
        System.out.println("[1] Log tasting");
        System.out.println("[2] View history by bottle");
        System.out.println("[3] View all tastings");
        System.out.println("[4] View top-rated");
        System.out.println("[5] Delete tasting log");
        System.out.println("[0] Back");
        System.out.println("Please make a selection:");
    }
}
