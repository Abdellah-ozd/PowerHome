package iut.dam.tp1powerhome.appliance;

public abstract class Appliance implements IAppliance {
    private String customName;
    private int labelResId;
    private int power;

    public Appliance(int labelResId, String customName, int power) {
        this.labelResId = labelResId;
        this.customName = customName;
        this.power = power;
    }

    @Override public String getCustomName() { return customName; }
    @Override public int getLabelResId() { return labelResId; }
    @Override public int getPower() { return power; }
}