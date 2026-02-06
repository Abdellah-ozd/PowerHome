package iut.dam.tp1powerhome;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // --- 1. CRÉATION DES DONNÉES (TP3b) ---
        List<Habitat> habitats = new ArrayList<>();

        Habitat h1 = new Habitat(1, "Gaëtan Leclair", 0, 45.0);
        h1.addAppliance(new Appliance("Lampe"));
        h1.addAppliance(new Appliance("TV"));
        h1.addAppliance(new Appliance("PC"));
        h1.addAppliance(new Appliance("Frigo"));
        habitats.add(h1);

        Habitat h2 = new Habitat(2, "Nacim Cheradi", 1, 30.0);
        h2.addAppliance(new Appliance("Micro-ondes"));
        habitats.add(h2);

        Habitat h3 = new Habitat(3, "Mehdi Mazouz", 2, 50.0);
        h3.addAppliance(new Appliance("Lave-linge"));
        h3.addAppliance(new Appliance("TV"));
        habitats.add(h3);

        Habitat h4 = new Habitat(4, "Abdellah Ouzidouh", 3, 60.0);
        h4.addAppliance(new Appliance("Four"));
        h4.addAppliance(new Appliance("PC"));
        h4.addAppliance(new Appliance("Lampe"));
        habitats.add(h4);


        // --- 2. CONFIGURATION DE LA LISTE ---
        ListView lvHabitats = findViewById(R.id.lv_habitats);

        // On branche l'adaptateur
        HabitatAdapter adapter = new HabitatAdapter(this, habitats);
        lvHabitats.setAdapter(adapter);

        // --- 3. GESTION DU CLIC ---
        // Consigne : "En cliquant... Afficher le nom du résident dans un Toast"
        lvHabitats.setOnItemClickListener((parent, view, position, id) -> {
            Habitat selectedHabitat = habitats.get(position);
            Toast.makeText(WelcomeActivity.this,
                    "Résident : " + selectedHabitat.getResidentName(),
                    Toast.LENGTH_SHORT).show();
        });
    }
}