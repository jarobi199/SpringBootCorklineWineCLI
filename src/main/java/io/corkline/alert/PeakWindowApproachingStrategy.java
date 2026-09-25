package io.corkline.alert;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.AlertType;
import io.corkline.enums.BottleStatus;
import io.corkline.enums.BottleType;
import io.corkline.interfaces.AlertStrategy;
import io.corkline.model.Bottle;
import io.corkline.model.DrinkingWindow;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class PeakWindowApproachingStrategy implements AlertStrategy {
    @Override
    public boolean supports(Bottle bottle) {
        return BottleStatus.IN_CELLAR.equals(bottle.getStatus()) &&
                (BottleType.SPARKLING_WINE.equals(bottle.getBottleType()) || BottleType.STILL_WINE.equals(bottle.getBottleType()));
    }

    @Override
    public List<AlertResult> evaluate(Bottle bottle) {
        List<AlertResult> results = new ArrayList<>();
        DrinkingWindow drinkingWindow = bottle.calculateDrinkingWindow();
        LocalDate peakStartYear = LocalDate.ofYearDay(drinkingWindow.peakStartYear(), 1);
        LocalDate startAlerting = peakStartYear.minusDays(SessionContext.getUser().getPeakAlertLeadDays());
        if (startAlerting.isBefore(LocalDate.now()) && LocalDate.now().isBefore(peakStartYear)) {
            long daysUntilPeakWindow = LocalDate.now().until(peakStartYear, ChronoUnit.DAYS);
            results.add(new AlertResult(bottle, AlertType.PEAK_WINDOW_APPROACHING, "The peak window will begin in " + daysUntilPeakWindow + " days!"));
        }

        return results;
    }

}
