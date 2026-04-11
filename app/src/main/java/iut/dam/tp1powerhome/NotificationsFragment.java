package iut.dam.tp1powerhome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class NotificationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        SharedPreferences prefs = requireActivity().getSharedPreferences("MesParametres", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        SwitchCompat switchMaster = view.findViewById(R.id.switch_master_notif);
        SwitchCompat switchAlertes = view.findViewById(R.id.switch_notif_alertes);
        SwitchCompat switchReservations = view.findViewById(R.id.switch_notif_reservations);
        SwitchCompat switchEcoCoins = view.findViewById(R.id.switch_notif_ecocoins);
        LinearLayout layoutSubNotifs = view.findViewById(R.id.layout_sub_notifs);

        boolean isMasterOn = prefs.getBoolean("notif_master", true);
        switchMaster.setChecked(isMasterOn);
        switchAlertes.setChecked(prefs.getBoolean("notif_alertes", true));
        switchReservations.setChecked(prefs.getBoolean("notif_reservations", true));
        switchEcoCoins.setChecked(prefs.getBoolean("notif_ecocoins", true));

        toggleSubOptions(layoutSubNotifs, isMasterOn);

        switchMaster.setOnCheckedChangeListener((btn, isChecked) -> {
            editor.putBoolean("notif_master", isChecked).apply();
            toggleSubOptions(layoutSubNotifs, isChecked);
        });

        switchAlertes.setOnCheckedChangeListener((btn, isChecked) -> editor.putBoolean("notif_alertes", isChecked).apply());
        switchReservations.setOnCheckedChangeListener((btn, isChecked) -> editor.putBoolean("notif_reservations", isChecked).apply());
        switchEcoCoins.setOnCheckedChangeListener((btn, isChecked) -> editor.putBoolean("notif_ecocoins", isChecked).apply());

        return view;
    }

    private void toggleSubOptions(LinearLayout layout, boolean isEnabled) {
        layout.setAlpha(isEnabled ? 1.0f : 0.5f);
        for (int i = 0; i < layout.getChildCount(); i++) {
            layout.getChildAt(i).setEnabled(isEnabled);
        }
    }
}