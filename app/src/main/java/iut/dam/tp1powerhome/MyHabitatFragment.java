package iut.dam.tp1powerhome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private int myHabitatId = 1; // Valeur par défaut si y'a un bug

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myhabitat, container, false);

        // 📌 ON LIT LE POST-IT ICI !
        android.content.SharedPreferences prefs = requireContext().getSharedPreferences("USER_DATA", android.content.Context.MODE_PRIVATE);
        myHabitatId = prefs.getInt("connected_habitat_id", 1); // Va chercher l'ID, sinon donne 1

        rvMyDevices = view.findViewById(R.id.rv_habitats);
        rvMyDevices.setLayoutManager(new LinearLayoutManager(getContext()));

        Button btnAdd = view.findViewById(R.id.btn_add_habitat);
        btnAdd.setText("Ajouter un appareil 🔌");

        loadMyData();

        btnAdd.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Bouton d'ajout prêt pour l'API !", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void loadMyData() {
        // L'appli utilise le BON numéro direct !
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

                        // Met à jour le titre
                        if(getActivity() != null) {
                            getActivity().setTitle("Ma Conso : " + total + " W");
                        }

                        // Affiche tes propres équipements
                        adapter = new ApplianceAdapter(getContext(), myDevices);
                        rvMyDevices.setAdapter(adapter);
                    }
                });
    }
}