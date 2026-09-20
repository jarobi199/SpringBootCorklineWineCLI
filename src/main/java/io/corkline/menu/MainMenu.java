package io.corkline.menu;

import io.corkline.interfaces.IMenu;
import io.corkline.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class MainMenu implements IMenu {
    @Autowired
    private AuthenticateMenu authenticateMenu;
    @Autowired
    private AlertMenu alertMenu;
    @Autowired
    private SettingsMenu settingsMenu;
    @Autowired
    private BottleMenu bottleMenu;
    @Autowired
    private LocationMenu locationMenu;
    @Autowired
    private TastingMenu tastingMenu;
    @Autowired
    private GoodbyeMenu goodbyeMenu;

    public void show() {
        int choice = 0;
        IMenu menu;

        System.out.println();
        displayTitle();
        authenticateMenu.automaticLogin();
        System.out.println();

        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            menu = switch (choice) {
                case 1 -> bottleMenu;
                case 2 -> locationMenu;
                case 3 -> tastingMenu;
                case 5 -> alertMenu;
                case 6 -> settingsMenu;
                case 0 -> goodbyeMenu;
                default -> throw new IllegalStateException("Unexpected value: " + choice);
            };
            menu.show();
        }
        while (choice != 0);

        InputHandler.closeInput();
    }

    public void printOptions() {
        System.out.println("[1] Bottles");
        System.out.println("[2] Locations");
        System.out.println("[3] Tastings");
        System.out.println("[4] Reports");
        System.out.println("[5] Alerts");
        System.out.println("[6] Settings");
        System.out.println("[0] Exit");
        System.out.println("Please make a selection:");
    }

    public void displayTitle() {
        System.out.println("==================================================================");
        System.out.println("   Welcome to the Corkline Wine And Spirits Manager Application!");
        System.out.println("==================================================================");
    }

}
