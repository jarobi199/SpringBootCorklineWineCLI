package io.corkline.window;

import io.corkline.interfaces.DrinkingWindowStrategy;
import io.corkline.model.DrinkingWindow;
import io.corkline.model.Spirit;

public class StableWindowStrategy  implements DrinkingWindowStrategy<Spirit> {
    @Override
    public DrinkingWindow calculate(Spirit spirit) {
        int peakStart = spirit.getDistillationYear();
        int peakEnd = peakStart + 100;

        return new DrinkingWindow(peakStart, peakEnd);
    }
}
