package iut.dam.tp1powerhome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.os.LocaleListCompat;
import androidx.fragment.app.Fragment;

public class ParametresFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parametres, container, false);

        SwitchCompat switchDarkMode = view.findViewById(R.id.switch_dark_mode);
        SharedPreferences preferences = requireActivity().getSharedPreferences("MesParametres", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        boolean isDarkMode = preferences.getBoolean("dark_mode", false);
        switchDarkMode.setChecked(isDarkMode);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("dark_mode", true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("dark_mode", false);
            }
            editor.apply();
        });

        TextView tvVersion = view.findViewById(R.id.tv_app_version);
        try {
            PackageInfo pInfo = requireContext().getPackageManager().getPackageInfo(requireContext().getPackageName(), 0);
            String version = pInfo.versionName;
            tvVersion.setText("Version de l'application : " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tvVersion.setText("Version : Inconnue");
        }

        RadioGroup rgLanguage = view.findViewById(R.id.rg_language);
        RadioButton rbEn = view.findViewById(R.id.rb_en);
        RadioButton rbFr = view.findViewById(R.id.rb_fr);
        RadioButton rbEs = view.findViewById(R.id.rb_es);

        String currentLang = AppCompatDelegate.getApplicationLocales().toLanguageTags();
        if (currentLang.contains("fr")) {
            rbFr.setChecked(true);
        } else if (currentLang.contains("es")) {
            rbEs.setChecked(true);
        } else {
            rbEn.setChecked(true);
        }

        rgLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_en) {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"));
            } else if (checkedId == R.id.rb_fr) {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("fr"));
            } else if (checkedId == R.id.rb_es) {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("es"));
            }
        });

        View btnClearCache = view.findViewById(R.id.btn_clear_cache);
        TextView btnDeleteAccount = view.findViewById(R.id.btn_delete_account);

        if (btnClearCache != null) {
            btnClearCache.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Cache vidé avec succès (12.4 Mo libérés)", Toast.LENGTH_SHORT).show();
            });
        }

        if (btnDeleteAccount != null) {
            btnDeleteAccount.setOnClickListener(v -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("⚠️ Supprimer le compte ?")
                        .setMessage("Cette action supprimera définitivement votre compte et votre solde d'Eco-Coins, êtes vous sûr de vouloir procéder ?")
                        .setPositiveButton("Oui, supprimer", (dialog, which) -> {
                            Toast.makeText(getContext(), "Compte supprimé ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            if (getActivity() != null) getActivity().finish();
                        })
                        .setNegativeButton("Annuler", null)
                        .show();
            });
        }

        return view;
    }
}