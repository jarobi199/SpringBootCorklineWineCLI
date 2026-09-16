package io.corkline.service;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.BottleStatus;
import io.corkline.enums.BottleType;
import io.corkline.model.Bottle;
import io.corkline.repository.BottleRepository;
import io.corkline.util.InputHandler;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class BottleService {
@Autowired
    private BottleRepository bottleRepository;

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

    public void listFavoritesAndLowStock() {
        List<Bottle> favorites = bottleRepository.findByUserIdAndIsFavorite(SessionContext.getUser().getId(), true);
        displayBottles(favorites, true);
    }

    private void displayBottles(List<Bottle> bottles, boolean highlightFavoriteLowStock) {
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
                bottleTable.row(bottle.getProducer(), bottle.getLabel(), bottle.getVintageYear(), quantity, String.valueOf(bottle.getBottleSize()), String.valueOf(bottle.getAbv()),
                        InputHandler.formatAsMoney(bottle.getPrice()), bottle.getPurchaseDate().toString());
            }
            bottleTable.render();
        }
    }

    private String highlightIfLowStock(Bottle bottle) {
        String quantity = String.valueOf(bottle.getQuantity());
        if(bottle.getQuantity() < SessionContext.getUser().getFavoriteStockThreshold()) {
            quantity = "[red, bold]" + bottle.getQuantity() + "[/]";
        }

        return quantity;
    }

}
