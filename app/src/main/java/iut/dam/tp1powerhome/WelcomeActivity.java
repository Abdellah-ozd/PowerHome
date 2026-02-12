package iut.dam.tp1powerhome;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import iut.dam.tp1powerhome.appliance.IAppliance;
import iut.dam.tp1powerhome.appliance.types.*;

public class WelcomeActivity extends AppCompatActivity {

    // Constantes pour éviter les nombres magiques
    private static final int POWER_THRESHOLD_DANGER = 6000;
    private static final int POWER_THRESHOLD_WARNING = 4000;
    private static final int INDIVIDUAL_APPLIANCE_THRESHOLD = 1500;

    private static final int COLOR_DANGER = Color.RED;
    private static final int COLOR_WARNING = Color.parseColor("#FFA500");
    private static final int COLOR_SUCCESS = Color.parseColor("#4CAF50");
    private static final int COLOR_NEUTRAL = Color.DKGRAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        List<Habitat> habitats = new ArrayList<>();

        // Jeffrey (RDC)
        Habitat h1 = new Habitat(1, "Jeffrey Xatab", 0, 45.0);
        h1.addAppliance(new Fridge("Frigo Américain", 350));
        h1.addAppliance(new Television("TV OLED 65\"", 200));
        h1.addAppliance(new GameConsole("PS5 + Son", 350));
        h1.addAppliance(new CoffeeMachine("Machine à Grain", 1450));
        h1.addAppliance(new Oven("Four Rotissoire", 2800));
        h1.addAppliance(new Lamp("Éclairage Salon", 60));
        habitats.add(h1);

        // Abdellah (1er)
        Habitat h2 = new Habitat(2, "Abdellah Ouzidouh", 1, 30.5);
        h2.addAppliance(new Computer("Tour Gaming RTX", 750));
        h2.addAppliance(new Microwave("Micro-ondes", 900));
        h2.addAppliance(new Heater("Radiateur Appoint", 2000));
        habitats.add(h2);

        // Mehdi (2e)
        Habitat h3 = new Habitat(3, "Mehdi Maouz", 2, 75.0);
        h3.addAppliance(new WashingMachine("Lave-linge 10kg", 2400));
        h3.addAppliance(new DishWasher("Lave-vaisselle Eco", 1800));
        h3.addAppliance(new Oven("Four Pyrolyse", 3000));
        h3.addAppliance(new Fridge("Réfrigérateur/Congélo", 300));
        h3.addAppliance(new Television("TV Salon", 150));
        h3.addAppliance(new Vaccum("Aspirateur Robot", 60));
        h3.addAppliance(new Heater("Sèche-Serviette", 1500));
        h3.addAppliance(new Heater("Chauffage d'appoint", 1200));
        h3.addAppliance(new Computer("PC Gamer", 900));
        h3.addAppliance(new GameConsole("PS5", 1500));
        habitats.add(h3);

        // Nacim (3e)
        Habitat h4 = new Habitat(4, "Nacim Cheradi", 3, 40.0);
        h4.addAppliance(new Computer("MacBook Pro M2", 70));
        h4.addAppliance(new Lamp("Philips Hue", 10));
        h4.addAppliance(new Microwave("Four Combiné", 1200));
        h4.addAppliance(new Heater("Clim Réversible", 900));
        h4.addAppliance(new CoffeeMachine("Petite Nespresso", 1100));
        habitats.add(h4);

        // Charlie (4e)
        Habitat h5 = new Habitat(5, "Charlie Dirt", 4, 90.0);
        h5.addAppliance(new Television("Home Cinema 5.1", 400));
        h5.addAppliance(new Fridge("Frigo Philips", 2000));
        h5.addAppliance(new Fridge("Mini-Frigo", 1000));
        h5.addAppliance(new Oven("Four à Pizza", 1500));
        h5.addAppliance(new GameConsole("Xbox Series X", 200));
        h5.addAppliance(new DishWasher("Lave-vaisselle", 2000));
        h5.addAppliance(new Lamp("Spots Plafond", 150));
        habitats.add(h5);

        // Paul (5e)
        Habitat h6 = new Habitat(6, "Paul Aernout", 5, 28.0);
        h6.addAppliance(new Computer("Vieux PC Portable", 90));
        h6.addAppliance(new CoffeeMachine("Cafetière Filtre", 800));
        h6.addAppliance(new Lamp("Lampe Halogène", 150));
        h6.addAppliance(new Television("Télé Cathodique", 100));
        h6.addAppliance(new Heater("Radiateur Huile", 1500));
        habitats.add(h6);

        // Local Technique
        Habitat h7 = new Habitat(7, "Services Généraux", 6, 10.0);
        h7.addAppliance(new IAppliance() {
            @Override public String getName() { return "VMC"; }
            @Override public int getPower() { return 450; }
            @Override public int getIconResId() { return android.R.drawable.ic_menu_rotate; }
        });
        h7.addAppliance(new Lamp("Couloir", 200));
        habitats.add(h7);

        // Tri automatique
        Collections.sort(habitats, (hab1, hab2) -> Integer.compare(hab1.getFloor(), hab2.getFloor()));

        ListView lvHabitats = findViewById(R.id.lv_habitats);
        HabitatAdapter adapter = new HabitatAdapter(this, habitats);
        lvHabitats.setAdapter(adapter);

        lvHabitats.setOnItemClickListener((parent, view, position, id) -> showHabitatDetails(habitats.get(position)));
    }

    private void showHabitatDetails(Habitat habitat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_habitat_details, null);

        TextView tvTitle = view.findViewById(R.id.tv_dialog_title);
        TextView tvArea = view.findViewById(R.id.tv_dialog_area);
        TextView tvFloor = view.findViewById(R.id.tv_dialog_floor);
        TextView tvTotalPower = view.findViewById(R.id.tv_dialog_total_power);
        LinearLayout llList = view.findViewById(R.id.ll_dialog_appliances);
        Button btnClose = view.findViewById(R.id.btn_close_dialog);

        tvTitle.setText(habitat.getResidentName());
        tvArea.setText(getString(R.string.dialog_area_format, habitat.getArea()));

        if (habitat.getFloor() == 0) {
            tvFloor.setText(getString(R.string.dialog_floor_rdc));
        } else {
            tvFloor.setText(getString(R.string.dialog_floor_format, habitat.getFloor()));
        }

        List<IAppliance> sortedList = new ArrayList<>(habitat.getAppliances());
        Collections.sort(sortedList, (a, b) -> b.getPower() - a.getPower());

        int totalWatts = 0;
        for (IAppliance app : sortedList) totalWatts += app.getPower();
        tvTotalPower.setText(getString(R.string.dialog_total_power_format, totalWatts));

        if (totalWatts >= POWER_THRESHOLD_DANGER) tvTotalPower.setTextColor(COLOR_DANGER);
        else if (totalWatts >= POWER_THRESHOLD_WARNING) tvTotalPower.setTextColor(COLOR_WARNING);
        else tvTotalPower.setTextColor(COLOR_SUCCESS);

        llList.removeAllViews();
        for (int i = 0; i < sortedList.size(); i++) {
            IAppliance app = sortedList.get(i);
            int color;
            if (app.getPower() >= INDIVIDUAL_APPLIANCE_THRESHOLD) color = COLOR_DANGER;
            else if (i == sortedList.size() - 1) color = COLOR_SUCCESS;
            else color = COLOR_NEUTRAL;

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            row.setPadding(0, 8, 0, 8);

            ImageView icon = new ImageView(this);
            int size = (int) (24 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(0, 0, 16, 0);
            icon.setLayoutParams(params);
            icon.setImageResource(app.getIconResId());
            icon.setColorFilter(color);

            TextView tvApp = new TextView(this);
            tvApp.setText(getString(R.string.appliance_detail_format, app.getName(), app.getPower()));
            tvApp.setTextSize(16);
            tvApp.setTextColor(color);

            row.addView(icon);
            row.addView(tvApp);
            llList.addView(row);
        }

        builder.setView(view);
        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnClose.setText(R.string.dialog_btn_close);
        btnClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}