package iut.dam.tp1powerhome;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
                            Log.e("API_POWERHOME", "ERREUR DE CONNEXION : ", e);
                            return;
                        }

                        Log.d("API_POWERHOME", "JSON REÇU : " + result);

                        List<Habitat> maListeDHabitats = Habitat.getListFromJson(result);

                        adapter = new HabitatAdapter(getContext(), maListeDHabitats);
                        rvHabitats.setAdapter(adapter);
                    }
                });
    }
}