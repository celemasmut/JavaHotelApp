package HotelApp.hotel;

public enum MealPlan {
    EP (": European Plan - Only room, without any meal"),
    CP (": Continental Plan - Only Breakfast"),
    MAP (": Modified American Plan - Breakfast and lunch or dinner"),
    AP (": American Plan - All meals");

    private final String description;

    MealPlan(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
