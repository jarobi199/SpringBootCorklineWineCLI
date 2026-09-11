package io.corkline;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorklineWineAndSpiritsManagerApp implements CommandLineRunner {

    static void main(String[] args) {
        SpringApplication.run(CorklineWineAndSpiritsManagerApp.class, args);
    }

    @Override
    public void run(String @NonNull ... args) {
        System.out.println("Wha Gwaan!!");
    }

}