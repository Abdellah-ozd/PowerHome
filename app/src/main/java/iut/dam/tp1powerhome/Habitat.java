package iut.dam.tp1powerhome;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import iut.dam.tp1powerhome.appliance.IAppliance;

public class Habitat implements Serializable {
    private int id;
    private String residentName;
    private int floor;
    private double area;

    // CHANGEMENT : On utilise l'interface IAppliance
    private List<IAppliance> appliances;

    public Habitat(int id, String residentName, int floor, double area) {
        this.id = id;
        this.residentName = residentName;
        this.floor = floor;
        this.area = area;
        this.appliances = new ArrayList<>();
    }

    // On accepte n'importe quoi qui respecte le contrat IAppliance
    public void addAppliance(IAppliance appliance) {
        this.appliances.add(appliance);
    }

    // --- GETTERS ---
    public String getResidentName() { return residentName; }
    public int getFloor() { return floor; }
    public double getArea() { return area; }
    public List<IAppliance> getAppliances() { return appliances; } // Retourne des IAppliance
    public String getFloorNumber() { return String.valueOf(floor); }

    public String getApplianceCountLabel() {
        int count = appliances.size();
        return count + " équipement" + (count > 1 ? "s" : "");
    }
}