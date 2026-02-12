package iut.dam.tp1powerhome.appliance;

// Elle implémente IAppliance mais reste abstraite (on ne peut pas faire new Appliance())
public abstract class Appliance implements IAppliance {
    protected String name;
    protected int power;

    public Appliance(String name, int power) {
        this.name = name;
        this.power = power;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPower() {
        return power;
    }

    // On ne définit PAS getIconResId() ici, on laisse les enfants (TV, Frigo...) le faire.
}