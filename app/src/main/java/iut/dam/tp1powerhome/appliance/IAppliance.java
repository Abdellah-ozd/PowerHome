package iut.dam.tp1powerhome.appliance;

import java.io.Serializable;

public interface IAppliance extends Serializable {
    String getName();
    int getPower();
    int getIconResId();
}