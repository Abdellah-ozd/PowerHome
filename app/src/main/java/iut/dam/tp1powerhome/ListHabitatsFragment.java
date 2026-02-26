package iut.dam.tp1powerhome;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
// 🔥 LES IMPORTS DU RECYCLERVIEW 🔥
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import iut.dam.tp1powerhome.appliance.IAppliance;
import iut.dam.tp1powerhome.appliance.types.*;

public class ListHabitatsFragment extends Fragment {

    private static final int POWER_THRESHOLD_DANGER = 6000;
    private static final int INDIVIDUAL_THRESHOLD = 1500;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 1. créer la vue xml
        View view = inflater.inflate(R.layout.fragment_listhabitats, container, false);

        List<Habitat> habitats = new ArrayList<>();

        // 1. Jeffrey (RDC)
        Habitat h1 = new Habitat(1, "Jeffrey Xatab", 0, 45.0);
        h1.addAppliance(new Fridge(R.string.device_fridge, "Frigo Américain", 350));
        h1.addAppliance(new Television(R.string.device_tv, "TV OLED 65\"", 200));
        h1.addAppliance(new GameConsole(R.string.device_game_console, "PS5 + Son", 350));
        h1.addAppliance(new CoffeeMachine(R.string.device_coffee_machine, "Machine à Grain", 1450));
        h1.addAppliance(new Oven(R.string.device_oven, "Four Rotissoire", 2800));
        h1.addAppliance(new Lamp(R.string.device_lamp, "Éclairage Salon", 60));
        habitats.add(h1);

        // 2. Abdellah (1er)
        Habitat h2 = new Habitat(2, "Abdellah Ouzidouh", 1, 30.5);
        h2.addAppliance(new Computer(R.string.device_pc, "Tour Gaming RTX", 750));
        h2.addAppliance(new Microwave(R.string.device_microwave, "Micro-ondes", 900));
        h2.addAppliance(new Heater(R.string.device_heater, "Radiateur Appoint", 2000));
        habitats.add(h2);

        // 3. Mehdi (2e)
        Habitat h3 = new Habitat(3, "Mehdi Maouz", 2, 75.0);
        h3.addAppliance(new WashingMachine(R.string.device_washing_machine, "Lave-linge 10kg", 2400));
        h3.addAppliance(new DishWasher(R.string.device_dishwasher, "Lave-vaisselle Eco", 1800));
        h3.addAppliance(new Oven(R.string.device_oven, "Four Pyrolyse", 3000));
        h3.addAppliance(new Fridge(R.string.device_fridge, "Réfrigérateur/Congélo", 300));
        h3.addAppliance(new Television(R.string.device_tv, "TV Salon", 150));
        h3.addAppliance(new Vaccum(R.string.device_vaccum, "Aspirateur Robot", 60));
        h3.addAppliance(new Heater(R.string.device_heater, "Sèche-Serviette", 1500));
        h3.addAppliance(new Heater(R.string.device_heater, "Chauffage d'appoint", 1200));
        h3.addAppliance(new Computer(R.string.device_pc, "PC Gamer", 900));
        h3.addAppliance(new GameConsole(R.string.device_game_console, "PS5", 1500));
        habitats.add(h3);

        // 4. Nacim (3e)
        Habitat h4 = new Habitat(4, "Nacim Cheradi", 3, 40.0);
        h4.addAppliance(new Computer(R.string.device_pc, "MacBook Pro M2", 70));
        h4.addAppliance(new Lamp(R.string.device_lamp, "Philips Hue", 10));
        h4.addAppliance(new Microwave(R.string.device_microwave, "Four Combiné", 1200));
        h4.addAppliance(new Heater(R.string.device_heater, "Clim Réversible", 900));
        h4.addAppliance(new CoffeeMachine(R.string.device_coffee_machine, "Petite Nespresso", 1100));
        habitats.add(h4);

        // 5. Charlie (4e)
        Habitat h5 = new Habitat(5, "Charlie Dirt", 4, 90.0);
        h5.addAppliance(new Television(R.string.device_tv, "Home Cinema 5.1", 400));
        h5.addAppliance(new Fridge(R.string.device_fridge, "Frigo Philips", 2000));
        h5.addAppliance(new Fridge(R.string.device_fridge, "Mini-Frigo", 1000));
        h5.addAppliance(new Oven(R.string.device_oven, "Four à Pizza", 1500));
        h5.addAppliance(new GameConsole(R.string.device_game_console, "Xbox Series X", 200));
        h5.addAppliance(new DishWasher(R.string.device_dishwasher, "Lave-vaisselle", 2000));
        h5.addAppliance(new Lamp(R.string.device_lamp, "Spots Plafond", 150));
        habitats.add(h5);

        // 6. Paul (5e)
        Habitat h6 = new Habitat(6, "Paul Aernout", 5, 28.0);
        h6.addAppliance(new Computer(R.string.device_pc, "Vieux PC Portable", 90));
        h6.addAppliance(new CoffeeMachine(R.string.device_coffee_machine, "Cafetière Filtre", 800));
        h6.addAppliance(new Lamp(R.string.device_lamp, "Lampe Halogène", 150));
        h6.addAppliance(new Television(R.string.device_tv, "Télé Cathodique", 100));
        h6.addAppliance(new Heater(R.string.device_heater, "Radiateur Huile", 1500));
        habitats.add(h6);

        // 7. Local Technique
        Habitat h7 = new Habitat(7, "Services Généraux", 6, 10.0);
        h7.addAppliance(new IAppliance() {
            @Override public String getCustomName() { return "VMC Centrale"; }
            @Override public int getLabelResId() { return R.string.device_heater; }
            @Override public int getPower() { return 450; }
            @Override public int getIconResId() { return android.R.drawable.ic_menu_rotate; }
        });
        h7.addAppliance(new Lamp(R.string.device_lamp, "Couloir", 200));
        habitats.add(h7);

        Collections.sort(habitats, (hab1, hab2) -> Integer.compare(hab1.getFloor(), hab2.getFloor()));

        // 🔥 LA DINGUERIE EST LÀ : On passe sur le moteur RecyclerView 🔥
        // Assure-toi que ton fichier fragment_listhabitats.xml utilise bien un RecyclerView
        // avec l'id : android:id="@+id/rv_habitats_list"
        RecyclerView rvHabitats = view.findViewById(R.id.rv_habitats_list);
        rvHabitats.setLayoutManager(new LinearLayoutManager(requireContext()));

        HabitatAdapter adapter = new HabitatAdapter(requireContext(), habitats);
        rvHabitats.setAdapter(adapter);

        // 🔥 LA LIGNE MAGIQUE POUR ACTIVER LE CLIC ET LA POP-UP 🔥
        adapter.setOnItemClickListener(habitatClique -> showHabitatDetails(habitatClique));
        // ⚠️ J'ai commenté ton clic car sur un RecyclerView faut faire une petite manip dans l'Adapter.
        // On gère l'affichage d'abord, on réactivera la pop-up des détails juste après !
        // lvHabitats.setOnItemClickListener((parent, v, position, id) -> showHabitatDetails(habitats.get(position)));

        return view;
    }

    private void showHabitatDetails(Habitat habitat) {
        // Ta pop-up est parfaite, on n'y touche pas !
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_habitat_details, null);

        TextView tvTitle = view.findViewById(R.id.tv_dialog_title);
        TextView tvTotalPower = view.findViewById(R.id.tv_dialog_total_power);
        LinearLayout llList = view.findViewById(R.id.ll_dialog_appliances);
        Button btnClose = view.findViewById(R.id.btn_close_dialog);

        tvTitle.setText(habitat.getResidentName());

        List<IAppliance> sortedList = new ArrayList<>(habitat.getAppliances());
        Collections.sort(sortedList, (a, b) -> b.getPower() - a.getPower());

        int totalWatts = 0;
        for (IAppliance app : sortedList) totalWatts += app.getPower();
        tvTotalPower.setText(getString(R.string.dialog_total_power_format, totalWatts));
        tvTotalPower.setTextColor(totalWatts > POWER_THRESHOLD_DANGER ? Color.RED : Color.parseColor("#4CAF50"));

        llList.removeAllViews();
        for (int i = 0; i < sortedList.size(); i++) {
            IAppliance app = sortedList.get(i);
            int color = (app.getPower() >= INDIVIDUAL_THRESHOLD) ? Color.RED : (i == sortedList.size() - 1 ? Color.parseColor("#4CAF50") : Color.DKGRAY);

            LinearLayout row = new LinearLayout(requireContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            row.setPadding(0, 15, 0, 15);

            ImageView icon = new ImageView(requireContext());
            int size = (int) (24 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(size, size);
            iconParams.setMargins(0, 0, 20, 0);
            icon.setLayoutParams(iconParams);
            icon.setImageResource(app.getIconResId());
            icon.setColorFilter(color);

            TextView tvCustom = new TextView(requireContext());
            tvCustom.setText(app.getCustomName());
            tvCustom.setTypeface(null, android.graphics.Typeface.BOLD);
            tvCustom.setTextSize(16);
            tvCustom.setTextColor(color);

            TextView tvLabel = new TextView(requireContext());
            tvLabel.setText(" [" + getString(app.getLabelResId()) + "]");
            tvLabel.setTextSize(13);
            tvLabel.setTextColor(Color.GRAY);

            TextView tvPower = new TextView(requireContext());
            tvPower.setText(app.getPower() + " W");
            tvPower.setTextSize(15);
            tvPower.setTextColor(color);

            View spacer = new View(requireContext());
            LinearLayout.LayoutParams spacerParams = new LinearLayout.LayoutParams(0, 0, 1f);
            spacer.setLayoutParams(spacerParams);

            row.addView(icon);
            row.addView(tvCustom);
            row.addView(tvLabel);
            row.addView(spacer);
            row.addView(tvPower);

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