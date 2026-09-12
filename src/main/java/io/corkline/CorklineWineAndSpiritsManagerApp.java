package io.corkline;

import io.corkline.menu.MainMenu;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorklineWineAndSpiritsManagerApp implements CommandLineRunner {

    @Autowired
    private MainMenu mainMenu;

    static void main(String[] args) {
        SpringApplication.run(CorklineWineAndSpiritsManagerApp.class, args);
    }

    @Override
    public void run(String @NonNull ... args) {
        mainMenu.show();
    }

}