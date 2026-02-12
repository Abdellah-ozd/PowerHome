package iut.dam.tp1powerhome;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Habitat implements Serializable {
    private int id;
    private String residentName;
    private int floor;
    private double area; // La variable est là
    private List<Appliance> appliances;

    public Habitat(int id, String residentName, int floor, double area) {
        this.id = id;
        this.residentName = residentName;
        this.floor = floor;
        this.area = area;
        this.appliances = new ArrayList<>();
    }

    // Ajoute un équipement
    public void addAppliance(Appliance appliance) {
        this.appliances.add(appliance);
    }

    // --- GETTERS ---

    public String getResidentName() {
        return residentName;
    }

    public int getFloor() {
        return floor;
    }

    // 👇 C'EST LUI QU'IL MANQUAIT ! 👇
    public double getArea() {
        return area;
    }

    // Utilisée par l'adaptateur pour la boucle des icônes
    public List<Appliance> getAppliances() {
        return appliances;
    }

    // Pour afficher juste le chiffre de l'étage
    public String getFloorNumber() {
        return String.valueOf(floor);
    }

    // Pour afficher "4 équipements"
    public String getApplianceCountLabel() {
        int count = appliances.size();
        return count + " équipement" + (count > 1 ? "s" : "");
    }
}