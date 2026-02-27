package iut.dam.tp1powerhome;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

public class MyHabitatFragment extends Fragment {

    private RecyclerView rvHabitats;
    private HabitatAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myhabitat, container, false);

        rvHabitats = view.findViewById(R.id.rv_habitats);
        rvHabitats.setLayoutManager(new LinearLayoutManager(getContext()));

        loadData();

        Button btnAdd = view.findViewById(R.id.btn_add_habitat);
        btnAdd.setOnClickListener(v -> {
            ajouterHabitatTest("Test", 9, 88.5, 4);
        });

        return view;
    }

    private void loadData() {
        String urlString = "http://10.0.2.2/powerhome/getHabitats.php";

        // Appel au serveur XAMPP
        Ion.with(this)
                .load(urlString)
                .asString()
                .withResponse() // Récupére la reponse pour gérer les erreurs
                .setCallback(new FutureCallback<com.koushikdutta.ion.Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> response) {
                        // 1. Gestion des gros crashs réseau (ex: pas de wifi, XAMPP éteint)
                        if (e != null) {
                            Log.e("API_POWERHOME", "ERREUR DE CONNEXION : ", e);
                            return;
                        }

                        // 2. Le contrôle de la douane (HTTP STATUS CODE)
                        if (response != null) {
                            int code = response.getHeaders().code();
                            Log.d("API_POWERHOME", "CODE HTTP REÇU : " + code);

                            if (code == 200) {
                                // BINGO ! Le code 200 veut dire que tout est nickel ✅
                                String result = response.getResult(); // On extrait le vrai texte JSON du colis
                                Log.d("API_POWERHOME", "JSON REÇU : " + result);

                                // On traduit le texte en objets Java
                                List<Habitat> maListeDHabitats = Habitat.getListFromJson(result);

                                // On branche l'Adapter
                                adapter = new HabitatAdapter(getContext(), maListeDHabitats);
                                rvHabitats.setAdapter(adapter);

                            } else if (code >= 400 && code < 500) {
                                // Erreur de ton côté (mauvaise URL, t'es pas autorisé...)
                                Log.e("API_POWERHOME", "EMBROUILLE CLIENT (Erreur " + code + ")");
                            } else if (code >= 500) {
                                // Erreur côté serveur (Ton PHP a craché)
                                Log.e("API_POWERHOME", "LE SERVEUR EST EN PLS (Erreur " + code + ")");
                            }
                        }
                    }
                });
    }

    // Envoyer des données
    private void ajouterHabitatTest(String nom, int etage, double surface, int nbEquipements) {

        String urlString = "http://10.0.2.2/powerhome/addHabitat.php" +
                "?floor=" + etage +
                "&area=" + surface +
                "&resident_name=" + android.net.Uri.encode(nom) +
                "&appliances_count=" + nbEquipements;

        Ion.with(this)
                .load(urlString)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<com.koushikdutta.ion.Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> response) {
                        if (e != null) {
                            Log.e("API_POWERHOME", "Erreur : ", e);
                            return;
                        }

                        if (response != null && response.getHeaders().code() == 200) {
                            Log.d("API_POWERHOME", "Locataire ajouté en BDD");

                            // Rechargement de la liste
                            loadData();
                        } else {
                            Log.e("API_POWERHOME", "Erreur lors de l'ajout Code : " + (response != null ? response.getHeaders().code() : "inconnu"));
                        }
                    }
                });
    }
}