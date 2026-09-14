package io.corkline.service;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.StorageType;
import io.corkline.model.CellarLocation;
import io.corkline.model.Range;
import io.corkline.repository.CellarLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CellarLocationService {
    @Autowired
    private CellarLocationRepository cellarLocationRepository;

    public void addCellarLocation(String description, StorageType storageType, int capacity, double minIdealTemp, double maxIdealTemp, double minIdealHumidity, double maxIdealHumidity) {
        Range idealTempRange = new Range(minIdealTemp, maxIdealTemp);
        Range idealHumidityRange = new Range(minIdealHumidity, maxIdealHumidity);
        CellarLocation cellarLocation = new CellarLocation(SessionContext.getUser().getId(), description, storageType, capacity, idealTempRange, idealHumidityRange);
        cellarLocationRepository.save(cellarLocation);
    }


}
