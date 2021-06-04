package HotelApp.util;

public enum MealPlan {
    EP (": European Plan - Only room, without any meal",0),
    CP (": Continental Plan - Only Breakfast",300),
    MAP (": Modified American Plan - Breakfast and lunch or dinner",500),
    AP (": American Plan - All meals",650);

    private final String description;
    private final double price;

    MealPlan(String description, double price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}
