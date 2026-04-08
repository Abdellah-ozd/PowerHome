package iut.dam.tp1powerhome;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import iut.dam.tp1powerhome.entities.Appliance;

public class EcoFragment extends Fragment {

    private LinearLayout listLayout;
    private String selectedDateSql;

    private class Slot {
        int heure;
        int charge;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eco, container, false);

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        listLayout = view.findViewById(R.id.ll_slots_container);

        selectedDateSql = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        loadPlanningForDate(selectedDateSql);

        calendarView.setOnDateChangeListener((cv, year, month, dayOfMonth) -> {
            selectedDateSql = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            loadPlanningForDate(selectedDateSql);
        });

        return view;
    }

    private void loadPlanningForDate(String date) {
        listLayout.removeAllViews();
        String url = "http://10.0.2.2/powerhome/getPlanning.php?date=" + date;

        Ion.with(this).load(url).asString().setCallback((e, result) -> {
            if (e != null || result == null) return;

            try {
                Type type = new TypeToken<List<Slot>>(){}.getType();
                List<Slot> slots = new Gson().fromJson(result, type);

                for (Slot slot : slots) {
                    Button btnSlot = new Button(requireContext());
                    btnSlot.setAllCaps(false);
                    String hourText = slot.heure + "h00 - " + (slot.heure + 1) + "h00";

                    if (slot.charge >= 70) {
                        btnSlot.setText("🔴 " + hourText + " (" + slot.charge + "%) - SATURÉ");
                        btnSlot.setBackgroundColor(Color.parseColor("#FFCDD2"));
                        btnSlot.setTextColor(Color.parseColor("#C62828"));
                        btnSlot.setEnabled(false);
                    } else if (slot.charge >= 30) {
                        btnSlot.setText("🟠 " + hourText + " (" + slot.charge + "%) - RÉSERVER (Moyen)");
                        btnSlot.setBackgroundColor(Color.parseColor("#FFE0B2"));
                        btnSlot.setTextColor(Color.parseColor("#EF6C00"));

                        btnSlot.setOnClickListener(v -> demanderEquipement(slot.heure, date));
                    } else {
                        btnSlot.setText("🟢 " + hourText + " (" + slot.charge + "%) - RÉSERVER (Idéal)");
                        btnSlot.setBackgroundColor(Color.parseColor("#C8E6C9"));
                        btnSlot.setTextColor(Color.parseColor("#2E7D32"));

                        btnSlot.setOnClickListener(v -> demanderEquipement(slot.heure, date));
                    }

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 0, 0, 15);
                    btnSlot.setLayoutParams(params);
                    listLayout.addView(btnSlot);
                }
            } catch (Exception ex) { ex.printStackTrace(); }
        });
    }

    private void demanderEquipement(int heure, String date) {
        int myHabitatId = requireContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE).getInt("connected_habitat_id", 1);
        String url = "http://10.0.2.2/powerhome/getEquipements.php?habitat_id=" + myHabitatId;

        Ion.with(this).load(url).asString().setCallback((e, result) -> {
            if (e != null || result == null) return;

            Type type = new TypeToken<List<Appliance>>(){}.getType();
            List<Appliance> mesEquipements = new Gson().fromJson(result, type);

            if (mesEquipements.isEmpty()) {
                Toast.makeText(getContext(), "Vous n'avez aucun équipement à réserver !", Toast.LENGTH_SHORT).show();
                return;
            }

            String[] nomsAppareils = new String[mesEquipements.size()];
            for (int i = 0; i < mesEquipements.size(); i++) {
                Appliance app = mesEquipements.get(i);
                nomsAppareils[i] = app.getNom() + " (" + app.getPuissanceWatts() + "W)";
            }

            final int[] choix = {0};

            new AlertDialog.Builder(requireContext())
                    .setTitle("Choisir l'équipement à utiliser")
                    .setSingleChoiceItems(nomsAppareils, 0, (dialog, which) -> {
                        choix[0] = which;
                    })
                    .setPositiveButton("Réserver le créneau", (dialog, which) -> {
                        Appliance appareilChoisi = mesEquipements.get(choix[0]);
                        // On envoie la puissance en base de données !
                        reserverEnBase(heure, date, appareilChoisi.getPuissanceWatts(), myHabitatId, appareilChoisi.getNom());
                    })
                    .setNegativeButton("Annuler", null)
                    .show();
        });
    }

    private void reserverEnBase(int heure, String date, int puissance, int habitatId, String nomAppareil) {
        String url = "http://10.0.2.2/powerhome/reserver.php?habitat_id=" + habitatId + "&date=" + date + "&heure=" + heure + "&puissance=" + puissance;

        Ion.with(this).load(url).asString().setCallback((e, result) -> {
            if (e == null) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("🌱 Réservation Validée")
                        .setMessage("Votre " + nomAppareil + " (" + puissance + "W) est programmé(e) à " + heure + "h00 le " + date + ".\n\nL'impact sur le réseau a été calculé. 🎁 +50 EcoCoins ! ")
                        .setPositiveButton("Génial", (dialog, which) -> {
                            loadPlanningForDate(date);
                        }).show();
            }
        });
    }
}