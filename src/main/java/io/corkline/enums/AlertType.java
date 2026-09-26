package io.corkline.enums;

public enum AlertType {
    PEAK_WINDOW_APPROACHING("Peak Window Approaching"),
    LOW_STOCK_FAVORITE("Low Stock Favorite"),
    STORAGE_CONDITION("Storage Condition Flag"),
    NOT_TASTED_RECENTLY("Not Tasted Recently"),
    DUPLICATE_LABEL("Duplicate Label");

    private final String displayName;

    AlertType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}