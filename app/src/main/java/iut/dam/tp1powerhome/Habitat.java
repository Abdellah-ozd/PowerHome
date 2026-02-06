package iut.dam.tp1powerhome;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Habitat implements Serializable {
    private int id;
    private String residentName;
    private int floor;
    private double area;
    private List<Appliance> appliances;

    public Habitat(int id, String residentName, int floor, double area) {
        this.id = id;
        this.residentName = residentName;
        this.floor = floor;
        this.area = area;
        this.appliances = new ArrayList<>();
    }

    // Méthode pour ajouter un équipement facilement
    public void addAppliance(Appliance appliance) {
        this.appliances.add(appliance);
    }

    // --- GETTERS & HELPER METHODS ---

    public String getResidentName() {
        return residentName;
    }

    public int getFloor() {
        return floor;
    }

    public double getArea() {
        return area;
    }

    // Helper pour afficher l'étage joliment (ex: "1er ETAGE")
    public String getFloorLabel() {
        return floor + (floor == 1 ? "er " : "e ") + "ETAGE";
    }

    // Helper pour compter les équipements (ex: "4 équipements")
    public String getApplianceCountLabel() {
        int count = appliances.size();
        return count + " équipement" + (count > 1 ? "s" : "");
    }
}