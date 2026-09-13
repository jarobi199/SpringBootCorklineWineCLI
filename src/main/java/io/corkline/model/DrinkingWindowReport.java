package io.corkline.model;

import io.corkline.enums.DrinkingWindowStatus;

public record DrinkingWindowReport(String bottleLabel, int vintageYear, int peakStartYear, int peakEndYear, DrinkingWindowStatus currentStatus, int yearsUntilPeak) {}
