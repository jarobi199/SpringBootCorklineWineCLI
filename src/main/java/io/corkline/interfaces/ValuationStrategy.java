package io.corkline.interfaces;

import io.corkline.model.Bottle;

public interface ValuationStrategy <T extends Bottle> {
    double calculate(T bottle);
}
