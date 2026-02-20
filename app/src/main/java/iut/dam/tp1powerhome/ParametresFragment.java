package iut.dam.tp1powerhome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ParametresFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // C'est ici qu'on gonfle (inflate) le fichier XML qu'on vient de créer
        return inflater.inflate(R.layout.fragment_parametres, container, false);
    }
}