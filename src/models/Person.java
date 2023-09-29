package models;

import java.time.LocalDate;

public abstract class Person {
    private int id;
    private String name;
    private String cpf;
    private LocalDate birthday;

    public Person(int id, String name, String cpf, LocalDate birthday){
        this.name = name;
        this.cpf = cpf;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getBirthday(){
        return birthday;
    }
}