package iut.dam.tp1powerhome.appliance.types;

import iut.dam.tp1powerhome.appliance.Appliance;

public class GameConsole extends Appliance {
    public GameConsole(String name, int power) {
        super(name, power);
    }

    @Override
    public int getIconResId() {
        return android.R.drawable.ic_media_play; // ▶️
    }
}