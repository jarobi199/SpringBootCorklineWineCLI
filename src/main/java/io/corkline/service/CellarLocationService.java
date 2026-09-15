package io.corkline.service;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.StorageType;
import io.corkline.model.CellarLocation;
import io.corkline.model.ConditionReading;
import io.corkline.model.Range;
import io.corkline.repository.CellarLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CellarLocationService {
    @Autowired
    private CellarLocationRepository cellarLocationRepository;

    public void addCellarLocation(String description, StorageType storageType, int capacity, int minIdealTemp, int maxIdealTemp, int minIdealHumidity, int maxIdealHumidity) {
        Range idealTempRange = new Range(minIdealTemp, maxIdealTemp);
        Range idealHumidityRange = new Range(minIdealHumidity, maxIdealHumidity);
        CellarLocation cellarLocation = new CellarLocation(SessionContext.getUser().getId(), description, storageType, capacity, idealTempRange, idealHumidityRange);
        cellarLocationRepository.save(cellarLocation);
    }

    public List<CellarLocation> getCellarLocations() {
        return cellarLocationRepository.findByUserId(SessionContext.getUser().getId());
    }

    public void recordConditionReading(CellarLocation cellarLocation, int currentTemp, int humidity, String note) {
        ConditionReading conditionReading = new ConditionReading(LocalDateTime.now(), currentTemp, humidity, note);
        cellarLocation.getReadings().add(conditionReading);
        cellarLocationRepository.save(cellarLocation);
    }
}
