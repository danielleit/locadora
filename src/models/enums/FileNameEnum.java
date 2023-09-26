package models.enums;

public enum FileNameEnum {
    MEDIA_FILE_NAME("media_items.txt"),
    CUSTOMER_FILE_NAME("customers.txt"),
    TRANSACTION_FILE_NAME("rental_transactions.txt"),
    CLERKS_FILE_NAME("rentar_clerks.txt");

    private String fileName;

    FileNameEnum(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}