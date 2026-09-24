package io.corkline.service;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.*;
import io.corkline.model.*;
import io.corkline.repository.BottleRepository;
import io.corkline.util.InputHandler;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class BottleService {
    @Autowired
    private BottleRepository bottleRepository;
    @Autowired
    private TastingLogService tastingLogService;

    public void listBottles(int filter, String value) {
        List<Bottle> bottles = new ArrayList<>();

        switch (filter) {
            case 1 -> bottles = bottleRepository.findByUserId(SessionContext.getUser().getId()).stream().sorted(Comparator.comparing(Bottle::getProducer)).toList();
            case 2 -> bottles = bottleRepository.findByUserId(SessionContext.getUser().getId())
                    .stream().filter(bottle -> bottle.getBottleType().equals(BottleType.valueOf(value.toUpperCase())))
                    .sorted(Comparator.comparing(Bottle::getProducer)).toList();
            case 3 -> bottles =  bottleRepository.findByUserId(SessionContext.getUser().getId())
                    .stream().filter(bottle -> bottle.getStatus().equals(BottleStatus.valueOf(value.toUpperCase())))
                    .sorted(Comparator.comparing(Bottle::getProducer)).toList();
        }

        displayBottles(bottles, false);
    }

    public List<Bottle> getAllBottles() {
        return bottleRepository.findByUserId(SessionContext.getUser().getId());
    }

    public void listFavoritesAndLowStock() {
        List<Bottle> favorites = bottleRepository.findByUserIdAndIsFavorite(SessionContext.getUser().getId(), true);
        displayBottles(favorites, true);
    }

    public void displayBottles(List<Bottle> bottles, boolean highlightFavoriteLowStock) {
        if(bottles.isEmpty()){
            System.out.println("No bottles found.");
        }
        else
        {
            System.out.println("| LIST OF BOTTLES |");
            Table bottleTable = Clique.table(TableType.BOX_DRAW)
                    .headers(
                            "[yellow, bold]PRODUCER[/]",
                            "[yellow, bold]LABEL[/]",
                            "[yellow, bold]VINTAGE YEAR[/]",
                            "[yellow, bold]QUANTITY[/]",
                            "[yellow, bold]BOTTLE SIZE[/]",
                            "[yellow, bold]ALCOHOL BY VOLUME[/]",
                            "[yellow, bold]PRICE[/]",
                            "[yellow, bold]PURCHASE DATE[/]"
                    );
            for (Bottle bottle : bottles) {
                String quantity = (highlightFavoriteLowStock) ? highlightIfLowStock(bottle) : String.valueOf(bottle.getQuantity());
                bottleTable.row(bottle.getProducer(), bottle.getLabel(), String.valueOf(bottle.getVintageYear()), quantity, String.valueOf(bottle.getBottleSize()), String.valueOf(bottle.getAbv()),
                        InputHandler.formatAsMoney(bottle.getPrice()), bottle.getPurchaseDate().toString());
            }
            bottleTable.render();
        }
    }

    private String highlightIfLowStock(Bottle bottle) {
        String quantity = String.valueOf(bottle.getQuantity());
        if(bottle.getQuantity() <= SessionContext.getUser().getFavoriteStockThreshold()) {
            quantity = "[red, bold]" + bottle.getQuantity() + "[/]";
        }

        return quantity;
    }

    public void addStillWineBottle(CellarLocation cellarLocation, String producer, String label, int vintageYear, int quantity, int bottleSize, double abv, double price, LocalDate purchaseDate,
                                   boolean isFavorite, String notes, String varietal, String region, WineColor wineColor, WineBodyStyle wineBodyStyle, int agingPotentialYears) {
        StillWine stillWine = new StillWine(SessionContext.getUser().getId(), cellarLocation.getId(), producer, label, vintageYear, quantity, bottleSize, abv, price, purchaseDate,
                isFavorite, BottleStatus.IN_CELLAR, notes, varietal, region, wineColor, wineBodyStyle, agingPotentialYears);
        bottleRepository.save(stillWine);
    }

    public void addSparklingWineBottle(CellarLocation cellarLocation, String producer, String label, int vintageYear, int quantity,
                                       int bottleSize, double abv, double price, LocalDate purchaseDate, boolean isFavorite, String notes, DosageLevel dosageLevel, ProductionMethod productionMethod, boolean isVintage) {
        SparklingWine sparklingWine = new SparklingWine(SessionContext.getUser().getId(), cellarLocation.getId(), producer, label, vintageYear, quantity, bottleSize, abv, price, purchaseDate,
                isFavorite, BottleStatus.IN_CELLAR, notes, dosageLevel, productionMethod, isVintage);
        bottleRepository.save(sparklingWine);
    }

    public void addSpiritBottle(CellarLocation cellarLocation, String producer, String label, int vintageYear, int quantity, int bottleSize, double abv,
                                double price, LocalDate purchaseDate, boolean isFavorite, String notes, SpiritType spiritType, int distillationYear, boolean caskStrength, int agedYears) {
        Spirit spirit = new Spirit(SessionContext.getUser().getId(), cellarLocation.getId(), producer, label, vintageYear, quantity, bottleSize, abv, price, purchaseDate,
                isFavorite, BottleStatus.IN_CELLAR, notes, spiritType, distillationYear, caskStrength, agedYears);
        bottleRepository.save(spirit);
    }

    public void moveBottle(CellarLocation cellarLocation, Bottle bottle) {
        bottle.setLocationId(cellarLocation.getId());
        bottleRepository.save(bottle);
    }

    public void consumeBottles(Bottle bottle, int amount) {
        bottle.setQuantity(Math.max((bottle.getQuantity() - amount), 0));

        if(bottle.getQuantity() == 0) {
            bottle.setStatus(BottleStatus.CONSUMED);
        }
        bottleRepository.save(bottle);
    }

    public void editStillWineBottle(Bottle bottle, int quantity, boolean isFavorite, String notes, int agingPotentialYears) {
        StillWine stillWineBottle = (StillWine) bottle;
        stillWineBottle.setQuantity(quantity);
        stillWineBottle.setFavorite(isFavorite);
        stillWineBottle.setNotes(notes);
        stillWineBottle.setAgingPotentialYears(agingPotentialYears);

        bottleRepository.save(stillWineBottle);
    }

    public void editSparklingWineBottle(Bottle bottle, int quantity, boolean isFavorite, String notes, DosageLevel dosageLevel) {
        SparklingWine sparklingWineBottle = (SparklingWine) bottle;
        sparklingWineBottle.setQuantity(quantity);
        sparklingWineBottle.setFavorite(isFavorite);
        sparklingWineBottle.setNotes(notes);
        sparklingWineBottle.setDosageLevel(dosageLevel);

        bottleRepository.save(sparklingWineBottle);
    }

    public void editSpiritBottle(Bottle bottle, int quantity, boolean isFavorite, String notes, boolean caskStrength) {
        Spirit spiritBottle = (Spirit) bottle;
        spiritBottle.setQuantity(quantity);
        spiritBottle.setFavorite(isFavorite);
        spiritBottle.setNotes(notes);
        spiritBottle.setCaskStrength(caskStrength);

        bottleRepository.save(spiritBottle);
    }

    public void viewBottleDetails(Bottle bottle) {
        System.out.println("| BOTTLE DETAILS |");
        System.out.println(bottle.getDetails());
        List<TastingLog> tastingLogs = tastingLogService.getAllTastingLogsByBottle(bottle);
        if (!tastingLogs.isEmpty()) {
            tastingLogService.displayTastingLogs(tastingLogs);
        }
    }

    public void deleteBottle(Bottle bottle) {
        tastingLogService.deleteTastingLogsByBottleId(bottle.getId());
        bottleRepository.delete(bottle);
    }

}
