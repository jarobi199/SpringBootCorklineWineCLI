package io.corkline.model;

import io.corkline.enums.BottleStatus;
import io.corkline.enums.BottleType;
import io.corkline.enums.SpiritType;

public class Spirit extends Bottle {
    private SpiritType spiritType;
    private int distillationYear;
    private boolean caskStrength;
    private int agedYears;

    public Spirit() {
        //No argument constructor
    }

    public Spirit(String userId, String locationId, String producer, String label, String vintageYear, int quantity, int bottleSize, int abv, double price, double purchaseDate,
                  boolean isFavorite, BottleStatus status, String notes, SpiritType spiritType, int distillationYear, boolean caskStrength, int agedYears) {
        super(userId, locationId, producer, label, vintageYear, quantity, bottleSize, abv, price, purchaseDate, isFavorite, status, notes);
        this.spiritType = spiritType;
        this.distillationYear = distillationYear;
        this.caskStrength = caskStrength;
        this.agedYears = agedYears;
    }

    public SpiritType getSpiritType() {
        return spiritType;
    }

    public void setSpiritType(SpiritType spiritType) {
        this.spiritType = spiritType;
    }

    public int getDistillationYear() {
        return distillationYear;
    }

    public void setDistillationYear(int distillationYear) {
        this.distillationYear = distillationYear;
    }

    public boolean isCaskStrength() {
        return caskStrength;
    }

    public void setCaskStrength(boolean caskStrength) {
        this.caskStrength = caskStrength;
    }

    public int getAgedYears() {
        return agedYears;
    }

    public void setAgedYears(int agedYears) {
        this.agedYears = agedYears;
    }

    @Override
    public DrinkingWindow calculateDrinkingWindow() {
        return null;
    }

    @Override
    public BottleType getBottleType() {
        return BottleType.SPIRIT;
    }
}
