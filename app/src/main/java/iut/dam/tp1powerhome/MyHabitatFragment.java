package iut.dam.tp1powerhome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.List;

import iut.dam.tp1powerhome.entities.Appliance;

public class MyHabitatFragment extends Fragment {

    private RecyclerView rvMyDevices;
    private ApplianceAdapter adapter;
    private int myHabitatId = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myhabitat, container, false);

        SharedPreferences prefs = requireContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        myHabitatId = prefs.getInt("connected_habitat_id", 1);

        rvMyDevices = view.findViewById(R.id.rv_habitats);
        rvMyDevices.setLayoutManager(new LinearLayoutManager(getContext()));

        Button btnAdd = view.findViewById(R.id.btn_add_habitat);
        btnAdd.setText("Ajouter un équipement 🔌");

        // Premier chargement de la page
        loadMyData();

        // Le VRAI formulaire de mise à jour dynamique
        btnAdd.setOnClickListener(v -> showAddDeviceForm());

        return view;
    }

    private void showAddDeviceForm() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText inputName = new EditText(getContext());
        inputName.setHint("Nom (ex: Aspirateur)");
        layout.addView(inputName);

        final EditText inputPower = new EditText(getContext());
        inputPower.setHint("Puissance en Watts (ex: 800)");
        inputPower.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(inputPower);

        new AlertDialog.Builder(requireContext())
                .setTitle("Nouvel Équipement")
                .setView(layout)
                .setPositiveButton("Ajouter", (dialog, which) -> {
                    String nom = inputName.getText().toString().trim();
                    String power = inputPower.getText().toString().trim();

                    if (!nom.isEmpty() && !power.isEmpty()) {
                        String url = "http://10.0.2.2/powerhome/addEquipement.php?habitat_id=" + myHabitatId
                                + "&nom=" + android.net.Uri.encode(nom)
                                + "&puissance=" + power;

                        Ion.with(this).load(url).asString().setCallback((e, result) -> {
                            if(e == null) {
                                Toast.makeText(getContext(), nom + " ajouté en BDD !", Toast.LENGTH_SHORT).show();
                                loadMyData(); // 🔄 Rechargement dynamique de la vue !
                            }
                        });
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void loadMyData() {
        String url = "http://10.0.2.2/powerhome/getEquipements.php?habitat_id=" + myHabitatId;
        Ion.with(this)
                .load(url)
                .asString()
                .setCallback((e, result) -> {
                    if (e == null && result != null) {
                        Type type = new TypeToken<List<Appliance>>(){}.getType();
                        List<Appliance> myDevices = new Gson().fromJson(result, type);

                        int total = 0;
                        for (Appliance a : myDevices) total += a.getPuissanceWatts();

                        if(getActivity() != null) getActivity().setTitle("Ma Conso totale : " + total + " W");

                        adapter = new ApplianceAdapter(getContext(), myDevices);
                        rvMyDevices.setAdapter(adapter);
                    }
                });
    }
}