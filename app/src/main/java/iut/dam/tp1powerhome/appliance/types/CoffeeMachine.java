package iut.dam.tp1powerhome.appliance.types;

import iut.dam.tp1powerhome.appliance.Appliance;

public class CoffeeMachine extends Appliance {
    public CoffeeMachine(String name, int power) {
        super(name, power);
    }

    @Override
    public int getIconResId() {
        return android.R.drawable.ic_menu_compass; // 🧭 (Un rond...)
    }
}