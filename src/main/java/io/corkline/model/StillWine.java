package io.corkline.model;

import io.corkline.enums.BottleStatus;
import io.corkline.enums.BottleType;
import io.corkline.enums.WineBodyStyle;
import io.corkline.enums.WineColor;
import io.corkline.interfaces.DrinkingWindowStrategy;
import io.corkline.window.BellCurveWindowStrategy;
import org.springframework.data.annotation.Transient;

import java.time.LocalDate;

public class StillWine extends Bottle {
    private String varietal;
    private String region;
    private WineColor wineColor;
    private WineBodyStyle bodyStyle;
    private int agingPotentialYears;
    @Transient
    private DrinkingWindowStrategy<StillWine>  drinkingWindowStrategy;

    public StillWine() {
        this.drinkingWindowStrategy = new BellCurveWindowStrategy();
    }

    public StillWine(String userId, String locationId, String producer, String label, int vintageYear, int quantity, int bottleSize, double abv, double price, LocalDate purchaseDate, boolean isFavorite,
                     BottleStatus status, String notes, String varietal, String region, WineColor wineColor, WineBodyStyle bodyStyle, int agingPotentialYears) {
        super(userId, locationId, producer, label, vintageYear, quantity, bottleSize, abv, price, purchaseDate, isFavorite, status, notes);
        this.varietal = varietal;
        this.region = region;
        this.wineColor = wineColor;
        this.bodyStyle = bodyStyle;
        this.agingPotentialYears = agingPotentialYears;
    }

    public String getVarietal() {
        return varietal;
    }

    public void setVarietal(String varietal) {
        this.varietal = varietal;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public WineColor getWineColor() {
        return wineColor;
    }

    public void setWineColor(WineColor wineColor) {
        this.wineColor = wineColor;
    }

    public WineBodyStyle getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(WineBodyStyle bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    public int getAgingPotentialYears() {
        return agingPotentialYears;
    }

    public void setAgingPotentialYears(int agingPotentialYears) {
        this.agingPotentialYears = agingPotentialYears;
    }

    @Override
    public String getDetails() {
        return super.getDetails() +
                "Varietal: " + varietal + "\n" +
                "Region: " + region + "\n" +
                "Wine Color: " + wineColor.name() + "\n" +
                "Wine Body Style: " + bodyStyle.name() + "\n" +
                "Aging Potential Years: " + agingPotentialYears + "\n";
    }

    @Override
    public DrinkingWindow calculateDrinkingWindow() {
        return drinkingWindowStrategy.calculate(this);
    }

    @Override
    public BottleType getBottleType() {
        return BottleType.STILL_WINE;
    }
}
