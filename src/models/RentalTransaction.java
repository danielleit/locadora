package models;

public class RentalTransaction {
    private int id;
    private Customer customer;
    private MediaItem item;
    private RentalClerk clerk;
    private long rentalDays;
    private double rentalCost;
    

    public RentalTransaction(int id, RentalClerk clerk, Customer customer, MediaItem item, long rentalDays) {
        this.id = id;
        this.customer = customer;
        this.item = item;
        this.clerk = clerk;
        this.rentalDays = rentalDays;
        this.rentalCost = item.calculateRentalCost(rentalDays);
    }

    public int getId() {
        return this.id;
    }

    public int getCustomerId() {
        return customer.getId();
    }

    public int getItemId() {
        return item.getId();
    }

    public long getRentalDays() {
        return rentalDays;
    }
    
    public double getRentalCost(){
        return rentalCost;
    }

    public int getClerkId(){
        return clerk.getId();
    }

    @Override
    public String toString() {
        return "Cliente: " + customer.getName() + " | Atendente: " + clerk.getName() + " | Item: " + item.getTitle() + " | Dias de locação: " + rentalDays + " | Total: R$ " + rentalCost;
    }
}