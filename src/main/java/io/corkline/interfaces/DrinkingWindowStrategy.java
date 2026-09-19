package io.corkline.interfaces;

import io.corkline.model.Bottle;
import io.corkline.model.DrinkingWindow;

public interface DrinkingWindowStrategy <T extends Bottle>{
    DrinkingWindow calculate(T bottle);
}
