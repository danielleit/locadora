package models;

import java.time.LocalDate;

public class RentalClerk extends Person {
    
    public RentalClerk(int id, String name, String cpf, LocalDate birthday){
        super(id, name, cpf, birthday);
    }
}
