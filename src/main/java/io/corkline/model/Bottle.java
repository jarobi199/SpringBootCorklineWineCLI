package io.corkline.model;

import io.corkline.enums.BottleStatus;
import io.corkline.enums.BottleType;
import io.corkline.util.InputHandler;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "bottles")
public abstract class Bottle {
    @Id
    protected String id;
    protected String userId;
    protected String locationId;
    protected String producer;
    protected String label;
    protected int vintageYear;
    protected int quantity;
    protected int bottleSize;
    protected int abv;
    protected double price;
    protected LocalDate purchaseDate;
    protected boolean isFavorite;
    protected BottleStatus status;
    protected String notes;

    public Bottle() {
        //No argument constructor
    }

    public Bottle(String userId, String locationId, String producer, String label, int vintageYear, int quantity, int bottleSize, int abv,
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

    public int getVintageYear() {
        return vintageYear;
    }

    public void setVintageYear(int vintageYear) {
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
    
    public String getDetails() {
        return
                "Producer: " + producer + "\n" +
                        "Label: " + label + "\n" +
                        "Vintage Year: " + vintageYear + "\n" +
                        "Quantity: " + quantity + "\n" +
                        "Bottle Size: " + bottleSize + "\n" +
                        "Alcohol By Volume (ABV): " + abv + "\n" +
                        "Price: " + InputHandler.formatAsMoney(price) + "\n" +
                        "Purchase Date: " + purchaseDate + "\n" +
                        "Is Favorite?: " + (isFavorite ? "Yes" : "No") + "\n" +
                        "Status: " + status + "\n" +
                        "Notes: " + notes + "\n" +
                        "Drinking Window: " + calculateDrinkingWindow().peakStartYear() + " - " + calculateDrinkingWindow().peakEndYear() + "\n";
    }

    public abstract DrinkingWindow calculateDrinkingWindow();

    public abstract BottleType getBottleType();
}
