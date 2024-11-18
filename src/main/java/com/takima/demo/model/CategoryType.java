package com.takima.demo.model;

public enum CategoryType {
    ECONOMY("Économie", 1.0),
    PREMIUM_ECONOMY("Premium Économie", 1.5),
    BUSINESS("Affaires", 2.0),
    FIRST_CLASS("Première Classe", 3.0);

    private final String displayName;
    private final double priceMultiplier;

    CategoryType(String displayName, double priceMultiplier) {
        this.displayName = displayName;
        this.priceMultiplier = priceMultiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }
}
