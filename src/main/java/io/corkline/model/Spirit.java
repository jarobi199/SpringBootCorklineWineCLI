package io.corkline.model;

import io.corkline.enums.BottleStatus;
import io.corkline.enums.BottleType;
import io.corkline.enums.SpiritType;
import io.corkline.interfaces.DrinkingWindowStrategy;
import io.corkline.window.StableWindowStrategy;
import org.springframework.data.annotation.Transient;

import java.time.LocalDate;

public class Spirit extends Bottle {
    private SpiritType spiritType;
    private int distillationYear;
    private boolean caskStrength;
    private int agedYears;
    @Transient
    private DrinkingWindowStrategy<Spirit> drinkingWindowStrategy;

    public Spirit() {
        this.drinkingWindowStrategy = new StableWindowStrategy();
    }

    public Spirit(String userId, String locationId, String producer, String label, int vintageYear, int quantity, int bottleSize, double abv, double price, LocalDate purchaseDate,
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
    public String getDetails() {
        return super.getDetails() +
                "Aged Years: " + agedYears  + "\n" +
                "Cask Strength: " + caskStrength  + "\n" +
                "Distillation Year: " + distillationYear + "\n";
    }

    @Override
    public DrinkingWindow calculateDrinkingWindow() {
        return drinkingWindowStrategy.calculate(this);
    }

    @Override
    public BottleType getBottleType() {
        return BottleType.SPIRIT;
    }
}
