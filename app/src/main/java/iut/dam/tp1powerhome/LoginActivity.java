package iut.dam.tp1powerhome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_pass);
        btnLogin = findViewById(R.id.btn_connect);
        TextView tvRegister = findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(v -> {
            startActivity(new android.content.Intent(LoginActivity.this, RegisterActivity.class));
        });
        btnLogin.setOnClickListener(v -> tenterLaConnexion());
    }

    private void tenterLaConnexion() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir vos identifiants", Toast.LENGTH_SHORT).show();
            return;
        }

        String urlString = "http://10.0.2.2/powerhome/login.php?email=" + email + "&password=" + password;

        Ion.with(this)
                .load(urlString)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<com.koushikdutta.ion.Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> response) {
                        if (e != null) {
                            Toast.makeText(LoginActivity.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (response != null && response.getHeaders().code() == 200) {
                            String result = response.getResult();
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                int idLocataire = jsonObject.getInt("habitat_id");

                                android.content.SharedPreferences prefs = getSharedPreferences("USER_DATA", android.content.Context.MODE_PRIVATE);
                                prefs.edit().putInt("connected_habitat_id", idLocataire).apply();

                                Toast.makeText(LoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } catch (Exception ex) {
                                Log.e("API_POWERHOME", "Erreur JSON : ", ex);
                                Toast.makeText(LoginActivity.this, "Erreur de lecture des données", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Identifiants incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}