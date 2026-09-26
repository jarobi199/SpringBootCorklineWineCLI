package io.corkline.model;

import io.corkline.enums.ValuationTrend;

public record ValuationReport(String bottleLabel, double purchasePrice, double currentValue, double gainLossAmount, double gainLossPercent, ValuationTrend trend) {}
