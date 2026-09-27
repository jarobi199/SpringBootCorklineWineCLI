package io.corkline.valuation;

import io.corkline.interfaces.ValuationStrategy;
import io.corkline.model.StillWine;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CompoundAppreciationStrategy implements ValuationStrategy<StillWine> {
    @Override
    public double calculate(StillWine stillWine) {
        long yearsSincePurchase = ChronoUnit.YEARS.between(stillWine.getPurchaseDate(), LocalDate.now());
        return stillWine.getPrice() * Math.pow(1.08, yearsSincePurchase);
    }
}
