package iut.dam.tp1powerhome;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog; // Important pour la Dialog
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // --- 1. CRÉATION DES DONNÉES (Avec la consommation en W) ---
        List<Habitat> habitats = new ArrayList<>();

        // Gaëtan (4 équipements)
        Habitat h1 = new Habitat(1, "Gaëtan Leclair", 0, 45.0);
        h1.addAppliance(new Appliance("Lampe", 60));
        h1.addAppliance(new Appliance("TV", 150));
        h1.addAppliance(new Appliance("PC Gamer", 400));
        h1.addAppliance(new Appliance("Frigo", 200));
        habitats.add(h1);

        // Cédric (1 équipement)
        Habitat h2 = new Habitat(2, "Cédric Boudet", 1, 30.5);
        h2.addAppliance(new Appliance("Micro-ondes", 800));
        habitats.add(h2);

        // Gaylord (2 équipements)
        Habitat h3 = new Habitat(3, "Gaylord Thibodeaux", 2, 50.0);
        h3.addAppliance(new Appliance("Lave-linge", 2000));
        h3.addAppliance(new Appliance("TV", 120));
        habitats.add(h3);

        // Adam (3 équipements)
        Habitat h4 = new Habitat(4, "Adam Jacquinot", 3, 62.0);
        h4.addAppliance(new Appliance("Four", 2500));
        h4.addAppliance(new Appliance("PC", 150));
        h4.addAppliance(new Appliance("Lampe", 40));
        habitats.add(h4);

        // Abel (1 équipement)
        Habitat h5 = new Habitat(5, "Abel Fresnel", 3, 25.0);
        h5.addAppliance(new Appliance("Radio", 20));
        habitats.add(h5);

        // --- 2. CONFIGURATION DE LA LISTE ---
        ListView lvHabitats = findViewById(R.id.lv_habitats);
        HabitatAdapter adapter = new HabitatAdapter(this, habitats);
        lvHabitats.setAdapter(adapter);

        // --- 3. GESTION DU CLIC -> OUVRIR LA DIALOG ---
        lvHabitats.setOnItemClickListener((parent, view, position, id) -> {
            Habitat selectedHabitat = habitats.get(position);
            showHabitatDetails(selectedHabitat); // On appelle notre méthode
        });
    }

    /**
     * Méthode pour afficher la Boîte de Dialogue (Popup)
     */
    private void showHabitatDetails(Habitat habitat) {
        // 1. On prépare le texte à afficher
        StringBuilder message = new StringBuilder();
        message.append("📍 Étage : ").append(habitat.getFloorNumber()).append("\n");
        message.append("📐 Surface : ").append(habitat.getArea()).append(" m²\n\n");

        message.append("🔌 Liste des équipements :\n");
        message.append("-------------------------\n");

        // On liste chaque appareil avec sa conso
        for (Appliance app : habitat.getAppliances()) {
            message.append("• ").append(app.getName())
                    .append(" (Conso : ").append(app.getPower()).append(" W)\n");
        }

        // 2. On construit la Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Détails : " + habitat.getResidentName()); // Titre
        builder.setMessage(message.toString());                     // Le texte qu'on a préparé
        builder.setIcon(android.R.drawable.ic_dialog_info);         // Petite icône (facultatif)

        // Bouton pour fermer
        builder.setPositiveButton("OK", null);

        // 3. On l'affiche
        builder.show();
    }
}