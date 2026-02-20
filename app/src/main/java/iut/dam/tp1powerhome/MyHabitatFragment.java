package iut.dam.tp1powerhome;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import iut.dam.tp1powerhome.entities.Habitat;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

public class MyHabitatFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 1. On crée la vue (on la met dans une variable au lieu de faire un return direct)
        View view = inflater.inflate(R.layout.fragment_myhabitat, container, false);

        // 2. 🔥 ON TOURNE LA CLÉ DE CONTACT ICI 🔥
        loadData();

        // 3. On renvoie la vue finie à l'écran
        return view;
    }

    private void loadData() {
        String urlString = "http://10.0.2.2/powerhome/getHabitats.php";

        Ion.with(this)
                .load(urlString)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            // Log.e pour les Erreurs (ça s'affiche en rouge)
                            Log.e("API_POWERHOME", "❌ ERREUR DE CONNEXION : ", e);
                            return;
                        }

                        // Log.d pour le Debug (ça s'affiche en bleu/vert)
                        Log.d("API_POWERHOME", "✅ JSON REÇU DU SERVEUR : " + result);

                        List<Habitat> maListeDHabitats = Habitat.getListFromJson(result);

                        Log.d("API_POWERHOME", "🚀 Nombre d'habitats traduits en Java : " + maListeDHabitats.size());
                    }
                });
    }
}