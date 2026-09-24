package io.corkline.alert;

import io.corkline.enums.AlertType;
import io.corkline.enums.BottleStatus;
import io.corkline.interfaces.AlertStrategy;
import io.corkline.model.Bottle;
import io.corkline.model.CellarLocation;
import io.corkline.model.ConditionReading;
import io.corkline.repository.CellarLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class StorageConditionStrategy implements AlertStrategy {
    @Autowired
    private CellarLocationRepository cellarLocationRepository;

    @Override
    public boolean supports(Bottle bottle) {
        return BottleStatus.IN_CELLAR.equals(bottle.getStatus());
    }

    @Override
    public List<AlertResult> evaluate(Bottle bottle) {
        List<AlertResult> results = new ArrayList<>();
        Optional<CellarLocation> cellarLocationOptional = cellarLocationRepository.findById(bottle.getLocationId());
        if (cellarLocationOptional.isPresent()) {
            CellarLocation cellarLocation = cellarLocationOptional.get();
            ConditionReading conditionReading = cellarLocation.getReadings().stream().max(Comparator.comparing(ConditionReading::dateTime)).orElse(null);
            if (conditionReading != null) {
                if (!cellarLocation.getIdealTemperatureC().contains(conditionReading.temperatureC())) {
                    results.add(new AlertResult(bottle, AlertType.STORAGE_CONDITION, "The current cellar location is out of the ideal temperature range! The temperature is " + conditionReading.temperatureC() + "C."));
                }
                if (!cellarLocation.getIdealHumidityPercent().contains(conditionReading.humidityPercent())) {
                    results.add(new AlertResult(bottle, AlertType.STORAGE_CONDITION, "The current humidity is out of the ideal range! The humidity is " + conditionReading.humidityPercent() + "%."));
                }
            }
        }

        return results;
    }

}
