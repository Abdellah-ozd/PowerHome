package iut.dam.tp1powerhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerDL;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer
        drawerDL = findViewById(R.id.drawerDL);
        NavigationView navNV = findViewById(R.id.nv_navigation);

        toggle = new ActionBarDrawerToggle(this, drawerDL, toolbar, R.string.open, R.string.close);
        drawerDL.addDrawerListener(toggle);
        toggle.syncState(); // Synchronise l'état du drawer


        // Clic listener pour le menu du drawer
        navNV.setNavigationItemSelectedListener(this);

        // Fragment par défaut au lancement
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, new ListHabitatsFragment())
                    .commit();
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

    // Navigation du drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //Fragment Listhabitats
        if (id == R.id.nav_habitats) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, new ListHabitatsFragment())
                    .commit();
        }
        //Fragment parametres
        else if (id == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, new ParametresFragment())
                    .commit();
        }
        //Fragment myHabitat
        else if (id == R.id.nav_my_habitat) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, new MyHabitatFragment())
                    .commit();
        }
        if (id == R.id.nav_logout) {
            // Départ vers loginactivtiy
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            // Tuer l'activity
            finish();
        }
        drawerDL.closeDrawer(GravityCompat.START);
        return true;
    }

}