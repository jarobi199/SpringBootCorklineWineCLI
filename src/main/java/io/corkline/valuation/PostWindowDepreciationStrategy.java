package io.corkline.valuation;

import io.corkline.interfaces.ValuationStrategy;
import io.corkline.model.DrinkingWindow;
import io.corkline.model.SparklingWine;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PostWindowDepreciationStrategy implements ValuationStrategy<SparklingWine>  {
    @Override
    public double calculate(SparklingWine bottle) {
        double currentValue =  0;
        DrinkingWindow drinkingWindow = bottle.calculateDrinkingWindow();
        LocalDate peakStartYear = LocalDate.of(drinkingWindow.peakStartYear(), 1, 1);
        LocalDate peakEndYear = LocalDate.of(drinkingWindow.peakEndYear(), 1, 1);
        if(peakStartYear.minusDays(1).isBefore(LocalDate.now()) && LocalDate.now().isBefore(peakEndYear.plusDays(1))) {
            currentValue = bottle.getPrice();
        }
        else if(LocalDate.now().isAfter(peakEndYear)) {
            long yearsPast = ChronoUnit.YEARS.between(LocalDate.now(), peakEndYear);
            currentValue = bottle.getPrice() * Math.max(0.5, 1 - (0.05 * yearsPast));
        }

        return currentValue;
    }

}


