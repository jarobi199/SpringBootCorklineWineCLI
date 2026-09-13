package io.corkline.model;

import io.corkline.enums.BottleType;

import java.util.Map;

public record CellarSummary(int totalBottles, int  totalUniqueLabels, double totalPurchaseValue, Map<BottleType, Double> valueByType) {}

