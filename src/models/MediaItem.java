package models;

public class MediaItem implements Rentable {
    
    private int id;
    private String title;
    private double price;
    private int duration;
    private int releaseYear;

    public MediaItem(int id, String title, double price, int duration, int releaseYear) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.duration = duration;
        this.releaseYear = releaseYear;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration(){
        return duration;
    }

    public int getReleaseYear(){
        return releaseYear;
    }

    public String toString() {
        return "ID: " + id + " | Título: " + title + " | Preço(diário): R$" + price + " | Duração: " + duration + " mins | Ano de Lançamento: " + releaseYear;
    }

    @Override
    public double calculateRentalCost(long days) {
        return getPrice() * days;
    }
}