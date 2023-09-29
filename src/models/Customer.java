package models;

import java.time.LocalDate;

public class Customer extends Person {
    
    public Customer(int id, String name, String cpf, LocalDate birthday) {
        super(id, name, cpf, birthday);
    }
}