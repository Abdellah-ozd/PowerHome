package iut.dam.tp1powerhome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // --- 1. PETITE ANIMATION SYMPA (FACULTATIF) ---
        // On récupère le logo via son ID (vérifie que c'est bien @+id/logo_splash dans ton XML)
        ImageView logo = findViewById(R.id.logo_splash);

        // Si tu as bien mis l'ID dans le XML, on lance l'animation
        if (logo != null) {
            logo.setAlpha(0f); // On le rend transparent
            logo.animate().alpha(1f).setDuration(1000).start(); // Il apparait en 1 seconde
        }

        // --- 2. LE TIMER (INDISPENSABLE) ---
        // C'est ça qui fait le changement de page
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // On prépare le voyage vers LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                // On décolle !
                startActivity(intent);
                // On ferme la SplashActivity pour ne pas pouvoir revenir dessus avec "Retour"
                finish();
            }
        };

        // On déclenche le chrono : 2000 millisecondes = 2 secondes
        new Handler().postDelayed(runnable, 2000);
    }
}