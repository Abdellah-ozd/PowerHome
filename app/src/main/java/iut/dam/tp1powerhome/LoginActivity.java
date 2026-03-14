package iut.dam.tp1powerhome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Assure-toi que c'est le bon nom de fichier XML

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_pass);
        btnLogin = findViewById(R.id.btn_connect);

        btnLogin.setOnClickListener(v -> tenterLaConnexion());
    }

    private void tenterLaConnexion() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Remplis tout frérot !", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. On fabrique l'URL du videur comme dans le PDF
        String urlString = "http://10.0.2.2/powerhome/login.php?email=" + email + "&password=" + password;

        // 2. On envoie la requête avec Ion
        Ion.with(this)
                .load(urlString)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<com.koushikdutta.ion.Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> response) {
                        if (e != null) {
                            Toast.makeText(LoginActivity.this, "Crash réseau 💥", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (response != null && response.getHeaders().code() == 200) {
                            // BINGO ! Le videur nous laisse rentrer
                            String result = response.getResult();
                            try {
                                // On découpe le JSON pour extraire le fameux Token VIP
                                JSONObject jsonObject = new JSONObject(result);
                                String tokenVIP = jsonObject.getString("token");

                                // 💡 TECHNIQUE DE DARON : On sauvegarde le Token dans le téléphone
                                SharedPreferences prefs = getSharedPreferences("PowerHomePrefs", MODE_PRIVATE);
                                prefs.edit().putString("MON_TOKEN_VIP", tokenVIP).apply();

                                Toast.makeText(LoginActivity.this, "Connexion réussie ✅", Toast.LENGTH_SHORT).show();

                                // On bascule sur l'écran principal (MainActivity)
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // On ferme l'écran de login pour pas qu'il revienne en arrière

                            } catch (Exception ex) {
                                Log.e("API_POWERHOME", "Erreur de lecture du Token : ", ex);
                            }
                        } else {
                            // Code 401 : Mauvais mdp
                            Toast.makeText(LoginActivity.this, "Identifiants foireux ❌", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}