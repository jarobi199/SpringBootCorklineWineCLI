package io.corkline.model;

import io.corkline.enums.BottleType;
import io.corkline.enums.WishlistPriority;
import io.corkline.enums.WishlistStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "wishlist_items")
public class WishlistItem {
    @Id
    private String id;
    private String userId;
    private String producer;
    private String label;
    private BottleType bottleType;
    private double targetPrice;
    private WishlistPriority priority;
    private String notes;
    private LocalDate dateAdded;
    private WishlistStatus status;

    public WishlistItem() {
        //No argument constructor
    }

    public WishlistItem(String userId, String producer, String label, BottleType bottleType, double targetPrice,
                        WishlistPriority priority, String notes, LocalDate dateAdded) {
        this.userId = userId;
        this.producer = producer;
        this.label = label;
        this.bottleType = bottleType;
        this.targetPrice = targetPrice;
        this.priority = priority;
        this.notes = notes;
        this.dateAdded = dateAdded;
        this.status = WishlistStatus.WANTED;
    }

    public WishlistStatus getStatus() {
        return status;
    }

    public void setStatus(WishlistStatus status) {
        this.status = status;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public WishlistPriority getPriority() {
        return priority;
    }

    public void setPriority(WishlistPriority priority) {
        this.priority = priority;
    }

    public double getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }

    public BottleType getBottleType() {
        return bottleType;
    }

    public void setBottleType(BottleType bottleType) {
        this.bottleType = bottleType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
