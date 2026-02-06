package iut.dam.tp1powerhome;

import java.io.Serializable;

public class Appliance implements Serializable {
    private String name;

    public Appliance(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}