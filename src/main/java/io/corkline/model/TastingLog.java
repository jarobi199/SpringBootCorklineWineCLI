package io.corkline.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "tasting_logs")
public class TastingLog {
    @Id
    private String id;
    private String bottleId;
    private String userId;
    private String bottleLabel;
    private LocalDate tastingDate;
    private int rating;
    private String notes;
    private String occasion;
    private int quantityConsumed;

    public TastingLog() {
        //No argument constructor
    }

    public TastingLog(String bottleId, String userId, String bottleLabel, LocalDate tastingDate, int rating, String notes,
                      String occasion, int quantityConsumed) {
        this.bottleId = bottleId;
        this.userId = userId;
        this.bottleLabel = bottleLabel;
        this.tastingDate = tastingDate;
        this.rating = rating;
        this.notes = notes;
        this.occasion = occasion;
        this.quantityConsumed = quantityConsumed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBottleId() {
        return bottleId;
    }

    public void setBottleId(String bottleId) {
        this.bottleId = bottleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBottleLabel() {
        return bottleLabel;
    }

    public void setBottleLabel(String bottleLabel) {
        this.bottleLabel = bottleLabel;
    }

    public LocalDate getTastingDate() {
        return tastingDate;
    }

    public void setTastingDate(LocalDate tastingDate) {
        this.tastingDate = tastingDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public int getQuantityConsumed() {
        return quantityConsumed;
    }

    public void setQuantityConsumed(int quantityConsumed) {
        this.quantityConsumed = quantityConsumed;
    }
}

