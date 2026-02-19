package iut.dam.tp1powerhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void test(View view) {
        // 1. Récupération des champs (compatible Portrait & Paysage)
        EditText etEmail = findViewById(R.id.et_email);
        EditText etPass = findViewById(R.id.et_pass);

        if (etEmail == null) etEmail = findViewById(R.id.et_email_land);
        if (etPass == null) etPass = findViewById(R.id.et_pass_land);

        // Sécurité anti-crash
        String email = (etEmail != null) ? etEmail.getText().toString() : "";
        String password = (etPass != null) ? etPass.getText().toString() : "";

        // 2. VERIFICATION (La consigne du TP3a)
        if (email.equals("") && password.equals("")) {
            // C'est gagné -> On prépare le voyage
            Intent intent = new Intent(this, WelcomeActivity.class);

            // On remplit le sac à dos (Bundle)
            Bundle bundle = new Bundle();
            bundle.putString("login", email);
            bundle.putString("password", password);
            intent.putExtras(bundle);

            // On décolle
            startActivity(intent);
        } else {
            // C'est perdu -> Petit message d'erreur
            Toast.makeText(this, "Erreur : Login ou MDP incorrect", Toast.LENGTH_SHORT).show();
        }
    }


}