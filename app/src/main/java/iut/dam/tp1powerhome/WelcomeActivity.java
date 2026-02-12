package iut.dam.tp1powerhome    ;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import iut.dam.tp1powerhome.appliance.types.*;
import iut.dam.tp1powerhome.appliance.*;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        List<Habitat> habitats = new ArrayList<>();

        // Gaëtan
        Habitat h1 = new Habitat(1, "Gaëtan Leclair", 0, 45.0);
        h1.addAppliance(new Television("TV Samsung", 150)); // Hérite de Appliance
        h1.addAppliance(new Computer("PC Gamer", 400));
        h1.addAppliance(new Fridge("Frigo Américain", 200));
        h1.addAppliance(new Lamp("Lampe Salon", 60));
        habitats.add(h1);

        // ... tes autres habitants (Cédric, Gaylord...) ...

        // Adam - DÉMO DU PRINCIPE DE SUBSTITUTION (LSP) avec ton Interface IAppliance
        Habitat h4 = new Habitat(4, "Adam Jacquinot", 3, 62.0);
        h4.addAppliance(new Computer("MacBook", 150));

        // Ici je crée un objet à la volée qui implémente directement IAppliance
        // sans passer par la classe abstraite Appliance !
        h4.addAppliance(new IAppliance() {
            @Override
            public String getName() { return "Compteur Linky"; }

            @Override
            public int getPower() { return 10; }

            @Override
            public int getIconResId() { return android.R.drawable.ic_lock_idle_charging; }
        });
        habitats.add(h4);

        // CONFIGURATION LISTE
        ListView lvHabitats = findViewById(R.id.lv_habitats);
        HabitatAdapter adapter = new HabitatAdapter(this, habitats);
        lvHabitats.setAdapter(adapter);

        lvHabitats.setOnItemClickListener((parent, view, position, id) -> {
            showHabitatDetails(habitats.get(position));
        });
    }

    private void showHabitatDetails(Habitat habitat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        android.view.View view = getLayoutInflater().inflate(R.layout.dialog_habitat_details, null);

        // Récupération des vues (inchangé)
        android.widget.TextView tvTitle = view.findViewById(R.id.tv_dialog_title);
        android.widget.TextView tvArea = view.findViewById(R.id.tv_dialog_area);
        android.widget.TextView tvFloor = view.findViewById(R.id.tv_dialog_floor);
        android.widget.LinearLayout llList = view.findViewById(R.id.ll_dialog_appliances);
        android.widget.Button btnClose = view.findViewById(R.id.btn_close_dialog);

        // Remplissage
        tvTitle.setText(habitat.getResidentName());
        tvArea.setText("📐 " + habitat.getArea() + " m²");
        tvFloor.setText("📍 Étage : " + habitat.getFloorNumber());

        // Tri sur la liste d'interface IAppliance
        List<IAppliance> sortedList = new ArrayList<>(habitat.getAppliances());
        Collections.sort(sortedList, (a, b) -> b.getPower() - a.getPower());

        llList.removeAllViews();

        for (int i = 0; i < sortedList.size(); i++) {
            IAppliance app = sortedList.get(i); // On manipule l'interface IAppliance

            // Création de la ligne (inchangé)
            android.widget.LinearLayout row = new android.widget.LinearLayout(this);
            row.setOrientation(android.widget.LinearLayout.HORIZONTAL);
            row.setGravity(android.view.Gravity.CENTER_VERTICAL);
            row.setPadding(0, 8, 0, 8);

            // Couleurs
            int color;
            if (i == 0) color = android.graphics.Color.RED;
            else if (i == sortedList.size() - 1) color = android.graphics.Color.parseColor("#4CAF50");
            else color = android.graphics.Color.DKGRAY;

            // Icône
            android.widget.ImageView icon = new android.widget.ImageView(this);
            int size = (int) (24 * getResources().getDisplayMetrics().density);
            android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(size, size);
            params.setMargins(0, 0, 16, 0);
            icon.setLayoutParams(params);

            icon.setImageResource(app.getIconResId()); // Appel via l'interface
            icon.setColorFilter(color);

            // Texte
            android.widget.TextView tvApp = new android.widget.TextView(this);
            tvApp.setText(app.getName() + " (" + app.getPower() + " W)");
            tvApp.setTextSize(16);
            tvApp.setTextColor(color);

            row.addView(icon);
            row.addView(tvApp);
            llList.addView(row);
        }

        builder.setView(view);
        AlertDialog dialog = builder.create();
        btnClose.setOnClickListener(v -> dialog.dismiss());
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        dialog.show();
    }
}