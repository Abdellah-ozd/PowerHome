package iut.dam.tp1powerhome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
// 🔥 L'IMPORT MAGIQUE EST LÀ 🔥
import androidx.appcompat.widget.SwitchCompat;

public class ParametresFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Création de la vue
        View view = inflater.inflate(R.layout.fragment_parametres, container, false);

        SwitchCompat switchDarkMode = view.findViewById(R.id.switch_dark_mode);

        // Carnet de préférences
        SharedPreferences preferences = requireActivity().getSharedPreferences("MesParametres", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        boolean isDarkMode = preferences.getBoolean("dark_mode", false);
        switchDarkMode.setChecked(isDarkMode);

        // Clic de l'interrupteur Dark Mode
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Mode nuit
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("dark_mode", true); // On note dans le carnet
            } else {
                // Mode jour
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("dark_mode", false);
            }
            editor.apply();
        });

        // Notifications
        SwitchCompat switchNotifs = view.findViewById(R.id.switch_notifications);

        boolean isNotifsEnabled = preferences.getBoolean("notifications", true);
        switchNotifs.setChecked(isNotifsEnabled);

        switchNotifs.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("notifications", isChecked);
            editor.apply();
        });

        // Affichage de la version
        TextView tvVersion = view.findViewById(R.id.tv_app_version);
        try {
            android.content.pm.PackageInfo pInfo = requireContext().getPackageManager().getPackageInfo(requireContext().getPackageName(), 0);
            String version = pInfo.versionName;
            tvVersion.setText("Version de l'application : " + version);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tvVersion.setText("Version : Inconnue");
        }

// --- GESTION DE LA LANGUE (TRILINGUE) ---
        android.widget.RadioGroup rgLanguage = view.findViewById(R.id.rg_language);
        android.widget.RadioButton rbEn = view.findViewById(R.id.rb_en);
        android.widget.RadioButton rbFr = view.findViewById(R.id.rb_fr);
        android.widget.RadioButton rbEs = view.findViewById(R.id.rb_es);

        String currentLang = androidx.appcompat.app.AppCompatDelegate.getApplicationLocales().toLanguageTags();
        if (currentLang.contains("fr")) {
            rbFr.setChecked(true);
        } else if (currentLang.contains("es")) {
            rbEs.setChecked(true);
        } else {
            rbEn.setChecked(true); // Si c'est vide ou autre chose, c'est l'Anglais par défaut
        }

        rgLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_en) {
                // Retour au default (Anglais)
                androidx.appcompat.app.AppCompatDelegate.setApplicationLocales(
                        androidx.core.os.LocaleListCompat.forLanguageTags("en")
                );
            } else if (checkedId == R.id.rb_fr) {
                // Mode FR
                androidx.appcompat.app.AppCompatDelegate.setApplicationLocales(
                        androidx.core.os.LocaleListCompat.forLanguageTags("fr")
                );
            } else if (checkedId == R.id.rb_es) {
                // Mode ESP
                androidx.appcompat.app.AppCompatDelegate.setApplicationLocales(
                        androidx.core.os.LocaleListCompat.forLanguageTags("es")
                );
            }
        });

        return view;
    }
}