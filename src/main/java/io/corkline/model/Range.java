package io.corkline.model;

public record Range(double min, double max) {
    public boolean contains(double value) {
        return min <= value && value <= max;
    }
}
