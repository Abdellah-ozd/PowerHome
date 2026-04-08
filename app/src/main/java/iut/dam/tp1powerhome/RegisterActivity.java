package iut.dam.tp1powerhome;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.koushikdutta.ion.Ion;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etFloor, etArea, etPass, etPassConfirm;
    private Button btnRegister;
    private TextView tvBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.et_reg_name);
        etEmail = findViewById(R.id.et_reg_email);
        etFloor = findViewById(R.id.et_reg_floor);
        etArea = findViewById(R.id.et_reg_area);
        etPass = findViewById(R.id.et_reg_pass);
        etPassConfirm = findViewById(R.id.et_reg_pass_confirm);

        btnRegister = findViewById(R.id.btn_register_submit);
        tvBackToLogin = findViewById(R.id.tv_back_to_login);

        btnRegister.setOnClickListener(v -> inscrireResident());

        tvBackToLogin.setOnClickListener(v -> finish());
    }

    private void inscrireResident() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String floor = etFloor.getText().toString().trim();
        String area = etArea.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String passConfirm = etPassConfirm.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || floor.isEmpty() || area.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Remplis tous les champs !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(passConfirm)) {
            Toast.makeText(this, "Les mots de passe ne correspondent pas ❌", Toast.LENGTH_SHORT).show();
            return;
        }


        String url = "http://10.0.2.2/powerhome/register.php"
                + "?name=" + android.net.Uri.encode(name)
                + "&email=" + android.net.Uri.encode(email)
                + "&floor=" + floor
                + "&area=" + area
                + "&password=" + android.net.Uri.encode(pass);

        Ion.with(this).load(url).asString().setCallback((e, result) -> {
            if (e != null) {
                Toast.makeText(this, "Erreur réseau 💥", Toast.LENGTH_SHORT).show();
                return;
            }

            if (result != null && result.contains("success")) {
                Toast.makeText(this, "Inscription réussie ! ✅", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
            }
        });
    }
}