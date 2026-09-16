package io.corkline.service;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.StorageType;
import io.corkline.model.CellarLocation;
import io.corkline.model.ConditionReading;
import io.corkline.model.Range;
import io.corkline.repository.BottleRepository;
import io.corkline.repository.CellarLocationRepository;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class CellarLocationService {
    @Autowired
    private CellarLocationRepository cellarLocationRepository;
    @Autowired
    private BottleRepository bottleRepository;

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

    public void displayCellarLocations() {
        List<CellarLocation> cellarLocations = cellarLocationRepository.findByUserId(SessionContext.getUser().getId())
                .stream().sorted(Comparator.comparing(CellarLocation::getName)).toList();
        if (cellarLocations.isEmpty()) {
            System.out.println("No locations found.");
        }
        else
        {
            System.out.println("| CELLAR LOCATIONS |");
            Table cellarLocationsTable = Clique.table(TableType.BOX_DRAW)
                    .headers(
                            "[yellow, bold]NAME[/]",
                            "[yellow, bold]STORAGE TYPE[/]",
                            "[yellow, bold]CAPACITY USED[/]",
                            "[yellow, bold]REMAINING CAPACITY[/]",
                            "[yellow, bold]TEMPERATURE[/]",
                            "[yellow, bold]HUMIDITY[/]"
                    );
            for (CellarLocation cellarLocation : cellarLocations) {
                int capacityUsed = bottleRepository.findByLocationId(cellarLocation.getId()).size();
                ConditionReading conditionReading = cellarLocation.getReadings().stream().max(Comparator.comparing(ConditionReading::dateTime)).orElse(null);
                String temperature = "N/A";
                String humidity = "N/A";
                if (conditionReading != null) {
                    temperature = highlightOutOfRange(cellarLocation.getIdealTemperatureC(), conditionReading.temperatureC());
                    humidity = highlightOutOfRange(cellarLocation.getIdealHumidityPercent(), conditionReading.humidityPercent());
                }
                cellarLocationsTable.row(cellarLocation.getName(), cellarLocation.getStorageType().name(), String.valueOf(capacityUsed),
                        String.valueOf(cellarLocation.getCapacity() - capacityUsed), temperature, humidity);
            }
            cellarLocationsTable.render();
        }
    }

    private String highlightOutOfRange(Range range, int value) {
        String quantity = String.valueOf(value);
        if(!range.contains(value)) {
            quantity = "[red, bold]" + value + "[/]";
        }

        return quantity;
    }

    public void viewLocationDetail(CellarLocation cellarLocation) {
        System.out.println("| CELLAR LOCATIONS |");
        Table cellarLocationsTable = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[yellow, bold]NAME[/]",
                        "[yellow, bold]STORAGE TYPE[/]",
                        "[yellow, bold]CAPACITY USED[/]",
                        "[yellow, bold]REMAINING CAPACITY[/]",
                        "[yellow, bold]IDEAL TEMPERATURE RANGE[/]",
                        "[yellow, bold]CURRENT TEMPERATURE[/]",
                        "[yellow, bold]IDEAL HUMIDITY RANGE[/]",
                        "[yellow, bold]CURRENT HUMIDITY[/]"
                );

        int capacityUsed = bottleRepository.findByLocationId(cellarLocation.getId()).size();
        ConditionReading conditionReading = cellarLocation.getReadings().stream().max(Comparator.comparing(ConditionReading::dateTime)).orElse(null);
        String temperatureRange = "N/A";
        String humidityRange = "N/A";
        String currentTemperature = "N/A";
        String currentHumidity = "N/A";
        if (conditionReading != null) {
            temperatureRange = cellarLocation.getIdealTemperatureC().min() + "C - " +  cellarLocation.getIdealTemperatureC().max() + "C";
            currentTemperature = highlightOutOfRange(cellarLocation.getIdealTemperatureC(), conditionReading.temperatureC());
            humidityRange = cellarLocation.getIdealHumidityPercent().min() + "% - " +  cellarLocation.getIdealHumidityPercent().max() + "%";
            currentHumidity = highlightOutOfRange(cellarLocation.getIdealHumidityPercent(), conditionReading.humidityPercent());
        }

        cellarLocationsTable.row(cellarLocation.getName(), cellarLocation.getStorageType().name(), String.valueOf(capacityUsed),
                String.valueOf(cellarLocation.getCapacity() - capacityUsed), temperatureRange, currentTemperature, humidityRange, currentHumidity);

        cellarLocationsTable.render();
    }

}
