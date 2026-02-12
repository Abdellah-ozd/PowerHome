package iut.dam.tp1powerhome;

import java.io.Serializable;

public class Appliance implements Serializable {
    private String name;   // Le Type (Ex: TV, Frigo...)
    private int power;     // La Conso (en Watts)

    public Appliance(String name, int power) {
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }
}