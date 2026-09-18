package io.corkline.model;

import io.corkline.enums.BottleStatus;
import io.corkline.enums.BottleType;
import io.corkline.enums.DosageLevel;
import io.corkline.enums.ProductionMethod;

import java.time.LocalDate;

public class SparklingWine extends Bottle {
    private DosageLevel dosageLevel;
    private ProductionMethod productionMethod;
    private boolean isVintage;

    public SparklingWine() {
        //No argument constructor
    }

    public SparklingWine(String userId, String locationId, String producer, String label, String vintageYear, int quantity, int bottleSize, int abv, double price, LocalDate purchaseDate,
                         boolean isFavorite, BottleStatus status, String notes, DosageLevel dosageLevel, ProductionMethod productionMethod, boolean isVintage) {
        super(userId, locationId, producer, label, vintageYear, quantity, bottleSize, abv, price, purchaseDate, isFavorite, status, notes);
        this.dosageLevel = dosageLevel;
        this.productionMethod = productionMethod;
        this.isVintage = isVintage;
    }

    public DosageLevel getDosageLevel() {
        return dosageLevel;
    }

    public void setDosageLevel(DosageLevel dosageLevel) {
        this.dosageLevel = dosageLevel;
    }

    public ProductionMethod getProductionMethod() {
        return productionMethod;
    }

    public void setProductionMethod(ProductionMethod productionMethod) {
        this.productionMethod = productionMethod;
    }

    public boolean isVintage() {
        return isVintage;
    }

    public void setVintage(boolean vintage) {
        isVintage = vintage;
    }

    public String getReleaseWarning() {
        DrinkingWindow window = calculateDrinkingWindow();
        return String.format(
                "Note: %s has a narrow peak window (%d–%d) . Sparkling "
                        + "wine doesn't improve with long cellaring, so plan to drink it within that range.",
                getLabel(), window.peakStartYear(), window.peakEndYear());
    }

    @Override
    public String getDetails() {
        return super.getDetails() +
                "Dosage Level: " + dosageLevel  + "\n" +
                "Production Method: " + productionMethod + "\n" +
                "Vintage? : " + (isVintage ? "Yes" : "No") + "\n";
    }

    @Override
    public DrinkingWindow calculateDrinkingWindow() {
        return null;
    }

    @Override
    public BottleType getBottleType() {
        return BottleType.SPARKLING_WINE;
    }
}
