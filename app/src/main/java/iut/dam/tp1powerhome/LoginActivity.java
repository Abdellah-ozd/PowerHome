package iut.dam.tp1powerhome;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void test(View view) {
        Log.i("LoginActivity:onClick", "Click connect");
        Toast t = Toast.makeText(view.getContext(), "Connexion impossible", Toast.LENGTH_LONG);
        t.show();
        Snackbar s = Snackbar.make(view, "Co impo", Snackbar.LENGTH_LONG);

    }
}