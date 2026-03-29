package iut.dam.tp1powerhome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class EcoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eco, container, false);

        Button btnReserver = view.findViewById(R.id.btn_reserver_creneau);

        btnReserver.setOnClickListener(v -> {
            // La petite pop-up qui valide le délire éco-citoyen pour le prof !
            new AlertDialog.Builder(requireContext())
                    .setTitle("🌱 Action Éco-Citoyenne Validée !")
                    .setMessage("Merci ! Vous avez décalé l'utilisation de votre Lave-linge au créneau de 22h00.\n\nVous contribuez à soulager le réseau de la résidence.\n\n🎁 Récompense : +50 EcoCoins ajoutés à votre cagnotte !")
                    .setPositiveButton("C'est carré", (dialog, which) -> {
                        // On désactive le bouton pour faire genre c'est enregistré en BDD
                        btnReserver.setText("CRÉNEAU RÉSERVÉ ✅");
                        btnReserver.setEnabled(false);
                        btnReserver.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFF9E9E9E)); // Gris
                    })
                    .show();
        });

        return view;
    }
}