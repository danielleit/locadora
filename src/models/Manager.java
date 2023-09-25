package models;

public class Manager extends Person{

    private int password;

    public Manager(int id, String name, String cpf, String birthday, int password) {
        super(id, name, cpf, birthday);
        this.password = password;
    }

    public int getPassword() {
            return password;
        }
}