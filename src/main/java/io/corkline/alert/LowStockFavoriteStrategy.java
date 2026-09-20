package io.corkline.alert;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.AlertType;
import io.corkline.enums.BottleStatus;
import io.corkline.interfaces.AlertStrategy;
import io.corkline.model.Bottle;

import java.util.ArrayList;
import java.util.List;

public class LowStockFavoriteStrategy implements AlertStrategy {
    @Override
    public boolean supports(Bottle bottle) {
        return (bottle.isFavorite()) && (BottleStatus.IN_CELLAR.equals(bottle.getStatus()));
    }

    @Override
    public List<AlertResult> evaluate(Bottle bottle) {
        List<AlertResult> results = new ArrayList<>();
        int quantity = bottle.getQuantity();
        if(quantity <= SessionContext.getUser().getFavoriteStockThreshold()) {
            results.add(new AlertResult(bottle, AlertType.LOW_STOCK_FAVORITE, "You are below your favorite stock threshold! There are only " + quantity + " bottles in your favorite stock."));
        }
        return  results;
    }
}
