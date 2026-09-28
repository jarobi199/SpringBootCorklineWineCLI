package io.corkline.alert;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.AlertType;
import io.corkline.enums.BottleStatus;
import io.corkline.interfaces.AlertStrategy;
import io.corkline.model.Bottle;
import io.corkline.model.CellarLocation;
import io.corkline.repository.BottleRepository;
import io.corkline.repository.CellarLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DuplicateLabelStrategy implements AlertStrategy {
    @Autowired
    private BottleRepository bottleRepository;
    @Autowired
    private CellarLocationRepository cellarLocationRepository;

    @Override
    public boolean supports(Bottle bottle) {
        return BottleStatus.IN_CELLAR.equals(bottle.getStatus());
    }

    @Override
    public List<AlertResult> evaluate(Bottle bottle) {
        List<AlertResult> results = new ArrayList<>();
        List<Bottle> bottles = bottleRepository.findByUserId(SessionContext.getUser().getId())
                .stream().filter(b -> BottleStatus.IN_CELLAR.equals(b.getStatus())).toList();
        for (Bottle b : bottles) {
            if((b.getProducer().equals(bottle.getProducer())) && (b.getLabel().equals(bottle.getLabel())) && !b.getLocationId().equals(bottle.getLocationId())) {
                CellarLocation cellarLocation = cellarLocationRepository.findById(b.getLocationId()).orElse(null);
                results.add(new AlertResult(bottle, AlertType.DUPLICATE_LABEL, "This label is a duplicate and already exists in the following location: " + (cellarLocation != null ? cellarLocation.getName() : "")));
            }
        }

        return  results;
    }
}
