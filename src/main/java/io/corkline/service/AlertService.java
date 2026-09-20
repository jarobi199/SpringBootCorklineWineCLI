package io.corkline.service;

import io.corkline.alert.AlertManager;
import io.corkline.alert.AlertResult;
import io.corkline.authentication.SessionContext;
import io.corkline.enums.AlertType;
import io.corkline.model.Bottle;
import io.corkline.repository.BottleRepository;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {
    @Autowired
    private BottleRepository bottleRepository;
    @Autowired
    private AlertManager alertManager;

    public void displayAlerts(AlertType alertType) {
        List<Bottle> bottles = bottleRepository.findByUserId(SessionContext.getUser().getId());
        List<AlertResult> results = alertManager.evaluate(bottles);
        if(alertType != null) {
            results = results.stream().filter(result -> result.alertType().equals(alertType)).toList();
        }
        displayAlertResults(results);
    }

    private void displayAlertResults(List<AlertResult> alertResults) {
        if(alertResults.isEmpty()) {
            System.out.println("No alerts found.\n");
        }
        else
        {
            System.out.println("| ALERTS |");
            Table table = Clique.table(TableType.BOX_DRAW)
                    .headers(
                            "[*blue, bold]ALERT TYPE[/]",
                            "[*blue, bold]BOTTLE[/]",
                            "[*blue, bold]MESSAGE[/]"
                    );
            for (AlertResult alertResult : alertResults) {
                table.row(alertResult.alertType().getDisplayName(), alertResult.bottle().getProducer()+ " - " + alertResult.bottle().getLabel(),  alertResult.message());
            }
            table.render();
        }
    }
}
