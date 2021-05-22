package HotelApp.hotel.bedrooms;

public enum ProductToConsume {
    MINIBAR(5000),
    ALCOHOLIC_DRINKS (1500),
    TOWEL(500),
    LAUNDRY(850),
    SNACKS(250);

    private final double price;

    ProductToConsume(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
