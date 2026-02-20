package iut.dam.tp1powerhome.entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Habitat {
    int id;
    int floor;
    double area;
    User resident;
    List<Appliance> appliances;

    // Méthode 1 : Convertir un texte JSON en un seul objet Habitat
    public static Habitat getFromJson(String json){
        Gson gson = new Gson();
        Habitat obj = gson.fromJson(json, Habitat.class);
        return obj;
    }

    // Méthode 2 : Convertir un gros texte JSON en une Liste d'Habitats
    public static List<Habitat> getListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Habitat>>(){}.getType();
        List<Habitat> list = gson.fromJson(json, type);
        return list;
    }
}