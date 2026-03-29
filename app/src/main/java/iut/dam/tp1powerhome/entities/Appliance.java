package iut.dam.tp1powerhome.entities;

import com.google.gson.annotations.SerializedName;

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

        String n = nom.toLowerCase();
        if (n.contains("frigo") || n.contains("réfrigérateur")) return android.R.drawable.ic_menu_save;
        if (n.contains("tv") || n.contains("télévision")) return android.R.drawable.ic_menu_view;
        if (n.contains("lave-linge") || n.contains("machine")) return android.R.drawable.ic_menu_rotate;
        if (n.contains("four") || n.contains("micro-ondes")) return android.R.drawable.ic_menu_day;
        if (n.contains("radiateur") || n.contains("chauffage")) return android.R.drawable.ic_menu_mylocation;
        if (n.contains("console") || n.contains("pc") || n.contains("ordinateur")) return android.R.drawable.ic_menu_compass;

        return android.R.drawable.ic_menu_info_details;
    }

    public String getDate_ajout() { return date_ajout; }
}