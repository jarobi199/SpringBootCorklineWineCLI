package io.corkline.menu;

import io.corkline.interfaces.IMenu;
import io.corkline.model.Bottle;
import io.corkline.service.ReportService;
import io.corkline.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportMenu implements IMenu {
    @Autowired
    private ReportService reportService;
    @Autowired
    private BottleMenu bottleMenu;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> cellarSummary();
                case 2 -> drinkingReportWindow();
                case 3 -> tastingRatingsByProducer();
                case 4 -> lowStockAndFavorites();
                case 5 -> storageConditions();
            }
        }
        while (choice != 0);
    }

    public void storageConditions() {
        reportService.generateStorageConditions();
    }

    public void lowStockAndFavorites() {
        reportService.generateLowStockAndFavorites();
    }

    public void tastingRatingsByProducer() {
        reportService.generateTastingRatingsByProducer();
    }

    public void drinkingReportWindow() {
        Bottle bottle = bottleMenu.listBottlesAndSelect();
        if (bottle != null) {
            reportService.generateDrinkingWindowReport(bottle);
        }
    }

    public void cellarSummary() {
        reportService.generateCellarSummary();
    }

    @Override
    public void printOptions() {
        System.out.println("[1] Cellar summary");
        System.out.println("[2] Drinking window report");
        System.out.println("[3] Tasting ratings by producer");
        System.out.println("[4] Low stock & favorites");
        System.out.println("[5] Storage conditions");
        System.out.println("[0] Back");
        System.out.println("Please make a selection:");
    }
}


