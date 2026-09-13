package io.corkline.model;

import io.corkline.enums.BottleStatus;
import io.corkline.enums.BottleType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "bottles")
public abstract class Bottle {
    @Id
    private String id;
    private String userId;
    private String locationId;
    private String producer;
    private String label;
    private String vintageYear;
    private int quantity;
    private int bottleSize;
    private int abv;
    private double price;
    private LocalDate purchaseDate;
    private boolean isFavorite;
    private BottleStatus status;
    private String notes;

    public Bottle() {
        //No argument constructor
    }

    public Bottle(String userId, String locationId, String producer, String label, String vintageYear, int quantity, int bottleSize, int abv,
                  double price, LocalDate purchaseDate, boolean isFavorite, BottleStatus status, String notes) {
        this.userId = userId;
        this.locationId = locationId;
        this.producer = producer;
        this.label = label;
        this.vintageYear = vintageYear;
        this.quantity = quantity;
        this.bottleSize = bottleSize;
        this.abv = abv;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.isFavorite = isFavorite;
        this.status = status;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getVintageYear() {
        return vintageYear;
    }

    public void setVintageYear(String vintageYear) {
        this.vintageYear = vintageYear;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBottleSize() {
        return bottleSize;
    }

    public void setBottleSize(int bottleSize) {
        this.bottleSize = bottleSize;
    }

    public int getAbv() {
        return abv;
    }

    public void setAbv(int abv) {
        this.abv = abv;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public BottleStatus getStatus() {
        return status;
    }

    public void setStatus(BottleStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public abstract DrinkingWindow calculateDrinkingWindow();

    public abstract BottleType getBottleType();
}
