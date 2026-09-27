package io.corkline.valuation;

import io.corkline.interfaces.ValuationStrategy;
import io.corkline.model.Spirit;

public class StableValuationStrategy  implements ValuationStrategy<Spirit> {
    @Override
    public double calculate(Spirit spirit) {
        return spirit.getPrice();
    }
}
