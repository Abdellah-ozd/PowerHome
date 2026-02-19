package iut.dam.tp1powerhome;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // L'import crucial !
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerDL;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 1. Setup de la Toolbar (la barre du haut)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 2. Setup du Drawer et du bouton Hamburger
        drawerDL = findViewById(R.id.drawerDL);
        NavigationView navNV = findViewById(R.id.nv_navigation);

        toggle = new ActionBarDrawerToggle(this, drawerDL, toolbar, R.string.open, R.string.close);
        drawerDL.addDrawerListener(toggle);
        toggle.syncState(); // Indispensable pour voir le hamburger !

        // 3. Activation du bouton menu dans la barre
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // 4. On branche l'écouteur de clics pour le menu
        navNV.setNavigationItemSelectedListener(this);

        // 5. Affichage du fragment par défaut au lancement
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, new HabitatsFragment())
                    .commit();
            // Optionnel : mettre l'item "Habitats" en surbrillance dans le menu
            navNV.setCheckedItem(R.id.nav_habitats);
        }
    }

    // Gestion du clic sur le bouton hamburger
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Logique de navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_habitats) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, new HabitatsFragment())
                    .commit();
        }
        // Tu pourras ajouter tes autres fragments ici plus tard (Paramètres, etc.)

        // On ferme le menu après le clic
        drawerDL.closeDrawer(GravityCompat.START);
        return true;
    }

}