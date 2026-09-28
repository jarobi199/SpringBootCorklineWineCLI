package io.corkline.alert;

import io.corkline.enums.BottleStatus;
import io.corkline.interfaces.AlertStrategy;
import io.corkline.model.Bottle;
import io.corkline.model.TastingLog;
import io.corkline.service.TastingLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotTastedRecentlyStrategy implements AlertStrategy {
    @Autowired
    private TastingLogService tastingLogService;

    @Override
    public boolean supports(Bottle bottle) {
        return BottleStatus.IN_CELLAR.equals(bottle.getStatus());
    }

    @Override
    public List<AlertResult> evaluate(Bottle bottle) {
        List<AlertResult> results = new ArrayList<>();
        TastingLog tastingLog =  tastingLogService.getMostRecentTastingLog();
        //TODO: Add code here

        return  results;
    }
}
