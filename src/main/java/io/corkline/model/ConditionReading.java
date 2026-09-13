package io.corkline.model;

import java.time.LocalDateTime;

public record ConditionReading (LocalDateTime dateTime, int temperatureC, int humidityPercent, String note) {}
