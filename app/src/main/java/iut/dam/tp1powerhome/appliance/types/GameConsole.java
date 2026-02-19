package iut.dam.tp1powerhome.appliance.types;
import iut.dam.tp1powerhome.appliance.Appliance;

public class GameConsole extends Appliance {
    public GameConsole(int labelResId, String customName, int power) {
        super(labelResId, customName, power);
    }
    @Override
    public int getIconResId() { return android.R.drawable.ic_menu_send; }
}