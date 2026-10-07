package io.corkline.alert;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.AlertType;
import io.corkline.enums.BottleStatus;
import io.corkline.interfaces.AlertStrategy;
import io.corkline.model.Bottle;
import io.corkline.model.TastingLog;
import io.corkline.service.TastingLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotTastedRecentlyStrategy implements AlertStrategy {
    @Autowired
    private TastingLogService tastingLogService;

    @Override
    public boolean supports(Bottle bottle) {
        return BottleStatus.IN_CELLAR.equals(bottle.getStatus());
    }

    @Override
    public List<AlertResult> evaluate(Bottle bottle) {
        List<AlertResult> results = new ArrayList<>();
        LocalDate date = bottle.getPurchaseDate();

        TastingLog tastingLog =  tastingLogService.findTastingLogsByBottleId(bottle.getId()).getFirst();
        if (tastingLog != null) {
            date = tastingLog.getTastingDate();
        }

        if(date.isBefore(LocalDate.now().minusYears(SessionContext.getUser().getNotTastedRecentlyThreshold()))) {
            results.add(new AlertResult(bottle, AlertType.NOT_TASTED_RECENTLY, "This bottle has not been tasted in over " + SessionContext.getUser().getNotTastedRecentlyThreshold() + " years!"));
        }

        return  results;
    }
}
