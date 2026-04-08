package iut.dam.tp1powerhome;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koushikdutta.ion.Ion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import iut.dam.tp1powerhome.entities.Appliance;

public class ApplianceAdapter extends RecyclerView.Adapter<ApplianceAdapter.ApplianceViewHolder> {
    private static final int SEUIL_ECO = 250;
    private static final int SEUIL_MOYEN = 600;

    private static final String COULEUR_VERT = "#2E7D32";
    private static final String COULEUR_ORANGE = "#F68B1E";
    private static final String COULEUR_ROUGE = "#D32F2F";
    private List<Appliance> appliances;
    private Context context;
    private int habitatId;
    private Runnable onDataChanged;

    public ApplianceAdapter(Context context, List<Appliance> appliances, int habitatId, Runnable onDataChanged) {
        this.context = context;
        this.habitatId = habitatId;
        this.onDataChanged = onDataChanged;

        if (appliances != null) {
            appliances.sort((a1, a2) -> Integer.compare(a1.getPuissanceWatts(), a2.getPuissanceWatts()));
        }
        this.appliances = appliances;

    }

    @NonNull
    @Override
    public ApplianceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appliance, parent, false);
        return new ApplianceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplianceViewHolder holder, int position) {
        Appliance app = appliances.get(position);
        int power = app.getPuissanceWatts();

        holder.tvName.setText(app.getNom());
        holder.tvPower.setText(app.getPuissanceWatts() + " W");
        holder.ivIcon.setImageResource(app.getIconResId());

        if (power <= SEUIL_ECO) {
            holder.tvPower.setTextColor(android.graphics.Color.parseColor("#2E7D32"));
        } else if (power <= SEUIL_MOYEN) {
            holder.tvPower.setTextColor(android.graphics.Color.parseColor("#F68B1E"));
        } else {
            holder.tvPower.setTextColor(android.graphics.Color.parseColor("#D32F2F"));
        }

        holder.itemView.setOnClickListener(v -> {

            // Calcul du nombre de jours depuis l'ajout
            long jours = 1;
            try {
                if (app.getDate_ajout() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date dateAjout = sdf.parse(app.getDate_ajout());
                    Date today = new Date();

                    long diff = today.getTime() - dateAjout.getTime();
                    jours = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                    // Si ajouté aujourd'hui, on compte au moins 1 jour de conso
                    if (jours <= 0) jours = 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Calcul de la conso totale estimée
            long consoTotale = jours * app.getPuissanceWatts();
            float consoKwh = consoTotale / 1000f; // Conversion en kWh

            // Message de la DialogBox
            String messageStats = "📅 Ajouté le : " + (app.getDate_ajout() != null ? app.getDate_ajout() : "Aujourd'hui") + "\n"
                    + "⏳ Temps écoulé : " + jours + " jour(s)\n"
                    + "⚡ Puissance : " + app.getPuissanceWatts() + " W\n\n"
                    + "📈 Conso estimée depuis l'ajout :\n"
                    + "🔥 " + consoTotale + " Watts (soit " + String.format(Locale.getDefault(), "%.2f", consoKwh) + " kWh)";

            new AlertDialog.Builder(context)
                    .setTitle("📊 Stats : " + app.getNom())
                    .setMessage(messageStats)
                    .setPositiveButton("Fermer", null)
                    .setNegativeButton("🗑️ Supprimer", (dialog, which) -> {
                        String url = "http://10.0.2.2/powerhome/deleteEquipement.php?habitat_id=" + habitatId + "&nom=" + android.net.Uri.encode(app.getNom());

                        Ion.with(context).load(url).asString().setCallback((e, result) -> {
                            if (e == null) {
                                Toast.makeText(context, app.getNom() + " retiré du réseau !", Toast.LENGTH_SHORT).show();
                                onDataChanged.run();
                            }
                        });
                    })
                    .show();
        });
    }



    @Override
    public int getItemCount() {
        return appliances == null ? 0 : appliances.size();
    }

    public static class ApplianceViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPower;
        ImageView ivIcon;

        public ApplianceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_appliance_name);
            tvPower = itemView.findViewById(R.id.tv_appliance_power);
            ivIcon = itemView.findViewById(R.id.iv_appliance_icon);
        }
    }
}