package iut.dam.tp1powerhome.entities;

import com.google.gson.annotations.SerializedName;

import iut.dam.tp1powerhome.R;

public class Appliance {
    private int id;
    private String nom;
    @SerializedName("puissance_watts")
    private int puissanceWatts;
    private String date_ajout;
    public String getNom() { return nom; }
    public int getPuissanceWatts() { return puissanceWatts; }

    public int getIconResId() {
        if (nom == null) return android.R.drawable.ic_menu_help;

        String n = nom.toLowerCase()
                .replace("é", "e")
                .replace("è", "e")
                .replace("ê", "e");

        if (n.contains("frigo") || n.contains("refrigerateur")) return R.drawable.ic_fridge;
        if (n.contains("tv") || n.contains("television") || n.contains("tele")) return R.drawable.ic_tv;
        if (n.contains("lave-linge") || n.contains("seche-linge") || n.contains("machine")) return R.drawable.ic_washing;
        if (n.contains("four") || n.contains("micro-ondes")) return R.drawable.ic_oven;
        if (n.contains("radiateur") || n.contains("chauffage")) return R.drawable.ic_heater;
        if (n.contains("console") || n.contains("playstation") || n.contains("pc") || n.contains("ordinateur")) return R.drawable.ic_console;
        if (n.contains("aspirateur")) return R.drawable.ic_vacuum;
        if (n.contains("reveil")) return R.drawable.ic_reveil;

        return android.R.drawable.ic_menu_info_details;
    }

    public String getDate_ajout() { return date_ajout; }
}