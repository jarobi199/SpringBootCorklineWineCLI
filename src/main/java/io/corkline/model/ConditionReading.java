package io.corkline.model;

public class ConditionReading {
    private String recordedAt;
    private int temperatureC;
    private int humidityPercent;
    private String note;

    public ConditionReading(String recordedAt, int temperatureC, int humidityPercent, String note) {
        this.recordedAt = recordedAt;
        this.temperatureC = temperatureC;
        this.humidityPercent = humidityPercent;
        this.note = note;
    }

    public String getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(String recordedAt) {
        this.recordedAt = recordedAt;
    }

    public int getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(int temperatureC) {
        this.temperatureC = temperatureC;
    }

    public int getHumidityPercent() {
        return humidityPercent;
    }

    public void setHumidityPercent(int humidityPercent) {
        this.humidityPercent = humidityPercent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
