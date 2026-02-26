package iut.dam.tp1powerhome;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import iut.dam.tp1powerhome.appliance.IAppliance;

public class Habitat implements Serializable {
    private int id;

    @SerializedName("resident_name")
    private String residentName;

    private int floor;
    private double area;

    private List<IAppliance> appliances;

    public Habitat(int id, String residentName, int floor, double area) {
        this.id = id;
        this.residentName = residentName;
        this.floor = floor;
        this.area = area;
        this.appliances = new ArrayList<>();
    }

    public void addAppliance(IAppliance appliance) {
        if (this.appliances == null) this.appliances = new ArrayList<>();
        this.appliances.add(appliance);
    }

    public String getResidentName() { return residentName; }
    public int getFloor() { return floor; }
    public double getArea() { return area; }

    public List<IAppliance> getAppliances() {
        if (appliances == null) return new ArrayList<>();
        return appliances;
    }

    public String getFloorNumber() { return String.valueOf(floor); }

    public String getApplianceCountLabel() {
        int count = getAppliances().size();
        return count + " équipement" + (count > 1 ? "s" : "");
    }

    public static Habitat getFromJson(String json){
        return new Gson().fromJson(json, Habitat.class);
    }

    public static List<Habitat> getListFromJson(String json) {
        Type type = new TypeToken<List<Habitat>>(){}.getType();
        return new Gson().fromJson(json, type);
    }
}