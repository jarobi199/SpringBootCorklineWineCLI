package io.corkline.window;

import io.corkline.interfaces.DrinkingWindowStrategy;
import io.corkline.model.DrinkingWindow;
import io.corkline.model.StillWine;

public class BellCurveWindowStrategy implements DrinkingWindowStrategy<StillWine>  {
    @Override
    public DrinkingWindow calculate(StillWine stillWine) {
        int peakStart = Math.toIntExact(stillWine.getVintageYear() + Math.round(stillWine.getAgingPotentialYears() * 0.4));
        int peakEnd = stillWine.getVintageYear() + stillWine.getAgingPotentialYears();

        return new DrinkingWindow(peakStart, peakEnd);
    }
}
