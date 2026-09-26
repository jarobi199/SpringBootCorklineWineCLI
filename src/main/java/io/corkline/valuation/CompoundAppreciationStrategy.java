package io.corkline.valuation;

import io.corkline.interfaces.ValuationStrategy;
import io.corkline.model.StillWine;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CompoundAppreciationStrategy implements ValuationStrategy<StillWine> {
    @Override
    public double calculate(StillWine bottle) {
        long yearsSincePurchase = ChronoUnit.YEARS.between(bottle.getPurchaseDate(), LocalDate.now());
        return bottle.getPrice() * Math.pow(1.08, yearsSincePurchase);
    }
}
