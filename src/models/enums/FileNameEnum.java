package models.enums;

public enum FileNameEnum {
    MEDIA_FILE_NAME("media_items.txt"),
    CUSTOMER_FILE_NAME("customers.txt"),
    RENTAL_FILE_NAME("rental_transactions.txt"),
    MANAGER_FILE_NAME("managers.txt");

    private String fileName;

    FileNameEnum(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}