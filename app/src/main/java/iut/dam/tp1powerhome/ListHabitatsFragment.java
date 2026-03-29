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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// On utilise les VRAIES entités propres !
import iut.dam.tp1powerhome.entities.Appliance;
import iut.dam.tp1powerhome.entities.Habitat;

public class ListHabitatsFragment extends Fragment {

    private static final int POWER_THRESHOLD_DANGER = 6000;
    private static final int INDIVIDUAL_THRESHOLD = 1500;

    private RecyclerView rvHabitats;
    private HabitatAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listhabitats, container, false);

        rvHabitats = view.findViewById(R.id.rv_habitats_list);
        rvHabitats.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Fini les données en dur, on tape dans l'API direct !
        loadData();

        return view;
    }

    private void loadData() {
        String urlString = "http://10.0.2.2/powerhome/getHabitats.php";

        Ion.with(this)
                .load(urlString)
                .asString()
                .withResponse()
                .setCallback((e, response) -> {
                    if (e != null) {
                        Toast.makeText(getContext(), "Erreur réseau !", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response != null && response.getHeaders().code() == 200) {
                        String result = response.getResult();
                        List<Habitat> maListeDHabitats = Habitat.getListFromJson(result);

                        Collections.sort(maListeDHabitats, (hab1, hab2) -> Integer.compare(hab1.getFloor(), hab2.getFloor()));

                        adapter = new HabitatAdapter(requireContext(), maListeDHabitats);
                        rvHabitats.setAdapter(adapter);

                        // 🔥 LA MASTERCLASS : On charge le matos en arrière-plan direct ! 🔥
                        for (int i = 0; i < maListeDHabitats.size(); i++) {
                            Habitat habitat = maListeDHabitats.get(i);
                            final int position = i; // On mémorise la case de la liste

                            String url = "http://10.0.2.2/powerhome/getEquipements.php?habitat_id=" + habitat.getId();

                            Ion.with(getContext())
                                    .load(url)
                                    .asString()
                                    .setCallback((ex, res) -> {
                                        if (ex == null) {
                                            Type type = new TypeToken<List<Appliance>>(){}.getType();
                                            List<Appliance> matos = new Gson().fromJson(res, type);
                                            habitat.setAppliances(matos);

                                            // On dit à l'Adapter de rafraîchir JUSTE cette case,
                                            // et BAM les icônes apparaissent toutes seules !
                                            adapter.notifyItemChanged(position);
                                        }
                                    });
                        }

                        // Vu qu'on a déjà chargé les équipements, le clic ouvre direct la pop-up !
                        adapter.setOnItemClickListener(habitat -> {
                            showHabitatDetails(habitat);
                        });
                    }
                });
    }

    // --- ICI ON RE-CONSTRUIT TA MASTERCLASS DE POP-UP COMPLEXE (ADAPTÉE AU NOUVEAU MODÈLE) ---
    private void showHabitatDetails(Habitat habitat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_habitat_details, null);

        TextView tvTitle = view.findViewById(R.id.tv_dialog_title);
        TextView tvTotalPower = view.findViewById(R.id.tv_dialog_total_power);
        LinearLayout llList = view.findViewById(R.id.ll_dialog_appliances);
        Button btnClose = view.findViewById(R.id.btn_close_dialog);

        tvTitle.setText(habitat.getResidentName());
        TextView tvFloor = view.findViewById(R.id.tv_dialog_floor);
        if (tvFloor != null) {
            if (habitat.getFloor() == 0) {
                tvFloor.setText(getString(R.string.dialog_floor_rdc));
            } else {
                tvFloor.setText(getString(R.string.dialog_floor_format, habitat.getFloor()));
            }
        }
        List<Appliance> sortedList = new ArrayList<>(habitat.getAppliances());
        // On trie du plus gourmand au moins gourmand
        Collections.sort(sortedList, (a, b) -> Integer.compare(b.getPuissanceWatts(), a.getPuissanceWatts()));

        int totalWatts = 0;
        for (Appliance app : sortedList) totalWatts += app.getPuissanceWatts();

        tvTotalPower.setText(getString(R.string.dialog_total_power_format, totalWatts));
        // Si ça dépasse, on met en rouge
        tvTotalPower.setTextColor(totalWatts > POWER_THRESHOLD_DANGER ? Color.RED : Color.parseColor("#4CAF50"));

        llList.removeAllViews();
        // On recrée la liste d'équipements avec tes couleurs stylées
        for (int i = 0; i < sortedList.size(); i++) {
            Appliance app = sortedList.get(i);

            // Si c'est un gros gourmand, on le met en rouge, sinon en gris
            int color = (app.getPuissanceWatts() >= INDIVIDUAL_THRESHOLD) ? Color.RED : (i == sortedList.size() - 1 ? Color.parseColor("#4CAF50") : Color.DKGRAY);

            LinearLayout row = new LinearLayout(requireContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            row.setPadding(0, 15, 0, 15);

            ImageView icon = new ImageView(requireContext());
            int size = (int) (24 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(size, size);
            iconParams.setMargins(0, 0, 20, 0);
            icon.setLayoutParams(iconParams);

            // On récupère la bonne icône
            icon.setImageResource(app.getIconResId());
            icon.setColorFilter(color);

            TextView tvCustom = new TextView(requireContext());
            tvCustom.setText(app.getNom());
            tvCustom.setTypeface(null, android.graphics.Typeface.BOLD);
            tvCustom.setTextSize(16);
            tvCustom.setTextColor(color);

            TextView tvPower = new TextView(requireContext());
            tvPower.setText(app.getPuissanceWatts() + " W");
            tvPower.setTextSize(15);
            tvPower.setTextColor(color);

            // Petit spacer pour caler la conso à droite
            View spacer = new View(requireContext());
            LinearLayout.LayoutParams spacerParams = new LinearLayout.LayoutParams(0, 0, 1f);
            spacer.setLayoutParams(spacerParams);

            row.addView(icon);
            row.addView(tvCustom);
            row.addView(spacer);
            row.addView(tvPower);

            llList.addView(row);
        }

        builder.setView(view);
        AlertDialog dialog = builder.create();
        // Pour avoir les coins arrondis si ton layout le gère
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnClose.setText(R.string.dialog_btn_close);
        btnClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}