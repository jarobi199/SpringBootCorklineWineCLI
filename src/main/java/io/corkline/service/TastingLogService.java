package io.corkline.service;

import io.corkline.authentication.SessionContext;
import io.corkline.model.Bottle;
import io.corkline.model.TastingLog;
import io.corkline.repository.BottleRepository;
import io.corkline.repository.TastingLogRepository;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class TastingLogService {
    @Autowired
    private TastingLogRepository tastingLogRepository;
    @Autowired
    private BottleRepository bottleRepository;

    public List<TastingLog> getAllTastingLogsByBottle(Bottle bottle) {
        return tastingLogRepository.findByBottleId(bottle.getId());
    }
    public void deleteTastingLogsByBottleId(String bottleId) {
        List<TastingLog>  tastingLogs = tastingLogRepository.findByBottleId(bottleId);
        tastingLogRepository.deleteAll(tastingLogs);
    }

    public void logTasting(Bottle bottle, LocalDate tastingDate, int rating, String notes, String occasion, int consumed) {
        TastingLog tastingLog = new TastingLog(bottle.getId(), SessionContext.getUser().getId(), bottle.getLabel(),  tastingDate, rating, notes, occasion, consumed);
        tastingLogRepository.save(tastingLog);

        bottle.setQuantity(bottle.getQuantity() - consumed);
        bottleRepository.save(bottle);
    }

    public void displayTastingLogs(List<TastingLog> tastingLogs) {
        System.out.println("| TASTING LOGS |");
        Table tastingLogTable = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[yellow, bold]BOTTLE LABEL[/]",
                        "[yellow, bold]TASTING DATE[/]",
                        "[yellow, bold]RATING[/]",
                        "[yellow, bold]NOTES[/]",
                        "[yellow, bold]OCCASION[/]",
                        "[yellow, bold]QUANTITY CONSUMED[/]"
                );
        tastingLogs.forEach(tastingLog -> tastingLogTable.row(tastingLog.getBottleLabel(), tastingLog.getTastingDate().toString(), String.valueOf(tastingLog.getRating()),
                tastingLog.getNotes(), tastingLog.getOccasion(), String.valueOf(tastingLog.getQuantityConsumed())));
        tastingLogTable.render();
    }

    public void viewHistoryByBottle(Bottle bottle) {
        List<TastingLog>  tastingLogs = tastingLogRepository.findByBottleId(bottle.getId()).stream().sorted(Comparator.comparing(TastingLog::getTastingDate).reversed()).toList();
        if (!tastingLogs.isEmpty()) {
            displayTastingLogs(tastingLogs);
            OptionalDouble averageDoubleOptional =  tastingLogs.stream().mapToInt(TastingLog::getRating).average();
            System.out.println("AVERAGE RATING: " + averageDoubleOptional.orElse(0));
            System.out.println();
        }
        else
        {
            System.out.println("There are no tasting logs to display");
        }
    }
}
