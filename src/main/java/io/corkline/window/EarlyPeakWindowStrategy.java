package io.corkline.window;

import io.corkline.interfaces.DrinkingWindowStrategy;
import io.corkline.model.DrinkingWindow;
import io.corkline.model.SparklingWine;

public class EarlyPeakWindowStrategy implements DrinkingWindowStrategy<SparklingWine> {
    @Override
    public DrinkingWindow calculate(SparklingWine sparklingWine) {
       int peakStart = (sparklingWine.getLabel().equalsIgnoreCase("NV")) ? sparklingWine.getVintageYear() + 1 : sparklingWine.getPurchaseDate().getYear() + 1;
       int peakEnd = peakStart + 3;

       return new DrinkingWindow(peakStart, peakEnd);
    }
}

