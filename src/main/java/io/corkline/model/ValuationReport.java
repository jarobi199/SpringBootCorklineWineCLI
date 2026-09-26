package io.corkline.model;

public record ValuationReport(String bottleLabel, double purchasePrice, double currentValue,
                              double gainLossAmount, double gainLossPercent, ValuationTrend trend) {}
