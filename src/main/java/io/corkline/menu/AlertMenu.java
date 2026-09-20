package io.corkline.menu;

import io.corkline.enums.AlertType;
import io.corkline.interfaces.IMenu;
import io.corkline.service.AlertService;
import io.corkline.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlertMenu implements IMenu {

    @Autowired
    private AlertService alertService;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> viewAllAlerts();
                case 2 -> viewPeakWindowAlerts();
                case 3 -> viewLowStockAlerts();
                case 4 -> viewStorageConditionsAlerts();
            }
        }
        while (choice != 0);
    }

    public void viewPeakWindowAlerts() {
        alertService.displayAlerts(AlertType.PEAK_WINDOW_APPROACHING);
    }

    public void viewLowStockAlerts() {
        alertService.displayAlerts(AlertType.LOW_STOCK_FAVORITE);
    }

    public void viewStorageConditionsAlerts() {
        alertService.displayAlerts(AlertType.STORAGE_CONDITION);
    }

    public void viewAllAlerts() {
        alertService.displayAlerts(null);
    }

    @Override
    public void printOptions() {
        System.out.println("[1] View all alerts");
        System.out.println("[2] Peak window alerts");
        System.out.println("[3] Low stock alerts");
        System.out.println("[4] Storage condition alerts");
        System.out.println("[0] Exit");
        System.out.println("Please make a selection:");
    }

}
