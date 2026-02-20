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
            editor.apply(); // Sauvegarde le carnet !
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

        // --- LA GESTION DE LA LANGUE (MASTERCLASS) ---
        android.widget.RadioGroup rgLanguage = view.findViewById(R.id.rg_language);
        android.widget.RadioButton rbFr = view.findViewById(R.id.rb_fr);
        android.widget.RadioButton rbEn = view.findViewById(R.id.rb_en);

        // On regarde quelle langue est actuellement activée dans le tel pour cocher le bon bouton
        String currentLang = androidx.appcompat.app.AppCompatDelegate.getApplicationLocales().toLanguageTags();
        if (currentLang.contains("en")) {
            rbEn.setChecked(true);
        } else {
            rbFr.setChecked(true);
        }

        // Quand tu cliques sur une des langues...
        rgLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_en) {
                // On force l'appli en Anglais !
                androidx.appcompat.app.AppCompatDelegate.setApplicationLocales(
                        androidx.core.os.LocaleListCompat.forLanguageTags("en")
                );
            } else if (checkedId == R.id.rb_fr) {
                // On force l'appli en Français !
                androidx.appcompat.app.AppCompatDelegate.setApplicationLocales(
                        androidx.core.os.LocaleListCompat.forLanguageTags("fr")
                );
            }
        });

        return view;
    }
}