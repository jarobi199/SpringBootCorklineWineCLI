package io.corkline.model;

import io.corkline.enums.BottleType;

import java.util.Map;

public record NetWorthSummary(double totalPurchaseValue, double totalCurrentValue, double totalGainLoss, Map<BottleType, Double> valueByType) {}


