package models;

import java.time.LocalDate;
import java.time.Period;

public class Customer extends Person {
    
    public Customer(int id, String name, String cpf, LocalDate birthday) {
        super(id, name, cpf, birthday);
    }
       
    @Override
    public int getAge(){
        return Period.between(this.getBirthday(), LocalDate.now()).getYears();
    }
}