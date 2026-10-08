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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        Set<String> otherLocationIds = bottleRepository.findByUserId(SessionContext.getUser().getId()).stream()
                .filter(b -> BottleStatus.IN_CELLAR.equals(b.getStatus()))
                .filter(b -> b.getProducer().equals(bottle.getProducer()) && b.getLabel().equals(bottle.getLabel()))
                .map(Bottle::getLocationId)
                .filter(locationId -> !locationId.equals(bottle.getLocationId()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        for (String locationId : otherLocationIds) {
            String locationName = cellarLocationRepository.findById(locationId)
                    .map(CellarLocation::getName)
                    .orElse("an unknown location");
            results.add(new AlertResult(bottle, AlertType.DUPLICATE_LABEL,
                    "This label is a duplicate and already exists in the following location: " + locationName));
        }

        return results;
    }
}
