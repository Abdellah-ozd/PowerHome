package iut.dam.tp1powerhome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import iut.dam.tp1powerhome.entities.Reservation;

public class ProfilFragment extends Fragment {

    private TextView tvEcoCoins, tvConso;
    private RecyclerView rvReservations;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        tvEcoCoins = view.findViewById(R.id.tvEcoCoinsProfil);
        tvConso = view.findViewById(R.id.tvConsoProfil);
        rvReservations = view.findViewById(R.id.rvReservations);
        rvReservations.setLayoutManager(new LinearLayoutManager(getContext()));

        chargerProfil();
        return view;
    }

    private void chargerProfil() {
        SharedPreferences prefs = getActivity().getSharedPreferences("PowerHomeSession", Context.MODE_PRIVATE);
        int habitatId = prefs.getInt("habitat_id", -1);

        if (habitatId == -1) {
            Toast.makeText(getContext(), "Erreur de session", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2/powerhome/getProfil.php?habitat_id=" + habitatId;

        Ion.with(this)
                .load(url)
                .asJsonObject()
                .setCallback((e, result) -> {
                    if (e != null || result == null) {
                        Toast.makeText(getContext(), "Erreur réseau", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (result.get("status").getAsString().equals("success")) {
                        tvEcoCoins.setText(result.get("eco_coins").getAsString() + " 🪙");
                        tvConso.setText(result.get("consommation_totale").getAsString() + " W");

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Reservation>>(){}.getType();
                        List<Reservation> mesReservations = gson.fromJson(result.get("reservations"), listType);

                        ReservationAdapter adapter = new ReservationAdapter(mesReservations);
                        rvReservations.setAdapter(adapter);
                    }
                });
    }
}