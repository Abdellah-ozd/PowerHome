package iut.dam.tp1powerhome.entities;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Habitat {
    @SerializedName("id")
    private int id;

    @SerializedName("floor")
    private int floor;

    @SerializedName("area")
    private double area;

    @SerializedName("resident_name")
    private String residentName;

    private List<Appliance> appliances;

    public int getId() { return id; }
    public int getFloor() { return floor; }
    public double getArea() { return area; }
    public String getResidentName() { return residentName; }

    public List<Appliance> getAppliances() {
        if (appliances == null) return new ArrayList<>();
        return appliances;
    }

    public void setAppliances(List<Appliance> appliances) {
        this.appliances = appliances;
    }

    public static List<Habitat> getListFromJson(String json) {
        Type type = new TypeToken<List<Habitat>>(){}.getType();
        return new Gson().fromJson(json, type);
    }
}