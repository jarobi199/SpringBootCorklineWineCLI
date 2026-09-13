package io.corkline.model;

import io.corkline.enums.StorageType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "cellar_locations")
public class CellarLocation {
    @Id
    private String id;
    private String userId;
    private String description;
    private StorageType storageType;
    private int  capacity;
    private Range idealTemperatureC;
    private Range idealHumidityPercent;
    private List<ConditionReading> readings;

    public CellarLocation() {
        //No argument constructor
    }

    public CellarLocation(String userId, String description, StorageType storageType, int capacity, Range idealTemperatureC, Range idealHumidityPercent) {
        this.userId = userId;
        this.description = description;
        this.storageType = storageType;
        this.capacity = capacity;
        this.idealTemperatureC = idealTemperatureC;
        this.idealHumidityPercent = idealHumidityPercent;
        this.readings = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Range getIdealTemperatureC() {
        return idealTemperatureC;
    }

    public void setIdealTemperatureC(Range idealTemperatureC) {
        this.idealTemperatureC = idealTemperatureC;
    }

    public Range getIdealHumidityPercent() {
        return idealHumidityPercent;
    }

    public void setIdealHumidityPercent(Range idealHumidityPercent) {
        this.idealHumidityPercent = idealHumidityPercent;
    }

    public List<ConditionReading> getReadings() {
        return readings;
    }

    public void setReadings(List<ConditionReading> readings) {
        this.readings = readings;
    }
}
