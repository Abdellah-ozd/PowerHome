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

public class EcoFragment extends Fragment {

    private LinearLayout listLayout;
    private String selectedDateSql;

    // Classe interne pour lire le JSON de getPlanning.php
    private class Slot {
        int heure;
        int charge;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 1. ON GONFLE LE XML ! 🎈 (Fini le code Java foireux pour l'UI)
        View view = inflater.inflate(R.layout.fragment_eco, container, false);

        // 2. ON RÉCUPÈRE LES ÉLÉMENTS
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        listLayout = view.findViewById(R.id.ll_slots_container);

        // 3. INITIALISATION DE LA DATE DU JOUR
        selectedDateSql = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        loadPlanningForDate(selectedDateSql);

        // 4. QUAND ON CLIQUE SUR UNE NOUVELLE DATE
        calendarView.setOnDateChangeListener((cv, year, month, dayOfMonth) -> {
            selectedDateSql = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            loadPlanningForDate(selectedDateSql);
        });

        return view;
    }

    private void loadPlanningForDate(String date) {
        listLayout.removeAllViews(); // On vide l'ancienne liste
        String url = "http://10.0.2.2/powerhome/getPlanning.php?date=" + date;

        Ion.with(this).load(url).asString().setCallback((e, result) -> {
            if (e != null || result == null) {
                Toast.makeText(getContext(), "Impossible de charger le planning", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                Type type = new TypeToken<List<Slot>>(){}.getType();
                List<Slot> slots = new Gson().fromJson(result, type);

                for (Slot slot : slots) {
                    Button btnSlot = new Button(requireContext());
                    btnSlot.setAllCaps(false);
                    String hourText = slot.heure + "h00 - " + (slot.heure + 1) + "h00";

                    // LES 3 COULEURS DU PROF EN TEMPS RÉEL
                    if (slot.charge >= 70) {
                        // ROUGE : SATURÉ (On bloque)
                        btnSlot.setText("🔴 " + hourText + " (" + slot.charge + "%) - SATURÉ");
                        btnSlot.setBackgroundColor(Color.parseColor("#FFCDD2"));
                        btnSlot.setTextColor(Color.parseColor("#C62828"));
                        btnSlot.setEnabled(false);
                    } else if (slot.charge >= 30) {
                        // ORANGE : MOYEN (On autorise la réservation !)
                        btnSlot.setText("🟠 " + hourText + " (" + slot.charge + "%) - RÉSERVER (Moyen)");
                        btnSlot.setBackgroundColor(Color.parseColor("#FFE0B2"));
                        btnSlot.setTextColor(Color.parseColor("#EF6C00"));
                        // 🔥 ON DÉBLOQUE LE BOUTON ICI 🔥
                        btnSlot.setEnabled(true);
                        btnSlot.setOnClickListener(v -> reserverEnBase(slot.heure, date));
                    } else {
                        // VERT : CREUX (Idéal)
                        btnSlot.setText("🟢 " + hourText + " (" + slot.charge + "%) - RÉSERVER (Idéal)");
                        btnSlot.setBackgroundColor(Color.parseColor("#C8E6C9"));
                        btnSlot.setTextColor(Color.parseColor("#2E7D32"));
                        btnSlot.setEnabled(true);
                        btnSlot.setOnClickListener(v -> reserverEnBase(slot.heure, date));
                    }

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 0, 0, 15);
                    btnSlot.setLayoutParams(params);
                    listLayout.addView(btnSlot);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void reserverEnBase(int heure, String date) {
        int myHabitatId = requireContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE).getInt("connected_habitat_id", 1);
        String url = "http://10.0.2.2/powerhome/reserver.php?habitat_id=" + myHabitatId + "&date=" + date + "&heure=" + heure;

        Ion.with(this).load(url).asString().setCallback((e, result) -> {
            if (e == null) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("🌱 Réservation Validée")
                        .setMessage("Machine programmée à " + heure + "h00 le " + date + ".\n\n🎁 +50 EcoCoins crédités !")
                        .setPositiveButton("Top", (dialog, which) -> {
                            loadPlanningForDate(date); // On recharge en direct
                        }).show();
            }
        });
    }
}