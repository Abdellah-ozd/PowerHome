package iut.dam.tp1powerhome.appliance.types;

import iut.dam.tp1powerhome.appliance.Appliance;

public class Microwave extends Appliance {
    public Microwave(String name, int power) {
        super(name, power);
    }

    @Override
    public int getIconResId() {
        return android.R.drawable.ic_menu_crop; // 🔲
    }
}