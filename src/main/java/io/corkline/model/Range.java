package io.corkline.model;

public record Range(int min, int max) {
    public boolean contains(int value) {
        return min <= value && value <= max;
    }
}
