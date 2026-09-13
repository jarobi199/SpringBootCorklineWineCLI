package io.corkline.service;

import io.corkline.authentication.SessionContext;
import io.corkline.model.Bottle;
import io.corkline.repository.BottleRepository;
import io.corkline.util.InputHandler;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BottleService {
@Autowired
    private BottleRepository bottleRepository;

    public void displayBottles() {
        List<Bottle> bottles = bottleRepository.findByUserId(SessionContext.getUser().getId());
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
                bottleTable.row(bottle.getProducer(), bottle.getLabel(), bottle.getVintageYear(), String.valueOf(bottle.getQuantity()), String.valueOf(bottle.getBottleSize()), String.valueOf(bottle.getAbv()),
                        InputHandler.formatAsMoney(bottle.getPrice()), bottle.getPurchaseDate().toString());
            }
            bottleTable.render();
        }
    }

}
