package models;

public class Movie extends MediaItem implements Rentable {

    public Movie(int id, String title, double price, int duration, int releaseYear) {
        super(id, title, price, duration, releaseYear);
    }

    @Override
    public double calculateRentalCost(int days) {
        return getPrice() * days;
    }
} 