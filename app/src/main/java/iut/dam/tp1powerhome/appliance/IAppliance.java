package iut.dam.tp1powerhome.appliance;

public interface IAppliance {
    String getCustomName(); // Le nom que tu choisis (ex: "MiniFrigo")
    int getLabelResId();    // La catégorie traduite (ex: R.string.device_fridge)
    int getPower();
    int getIconResId();
}