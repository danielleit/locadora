package models;

public class RentalTransaction {
    private int customerId;
    private int itemId;
    private int rentalDays;
    public double RentalCost;

    public RentalTransaction(int customerId, int itemId, int rentalDays) {
        this.customerId = customerId;
        this.itemId = itemId;
        this.rentalDays = rentalDays;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    @Override
    public String toString() {
        return "ID do cliente: " + customerId + " | ID do item: " + itemId + " | Dias de locação: " + rentalDays;
    }
    public double calculateRentalCost(int days){
        return RentalCost;
    };
}