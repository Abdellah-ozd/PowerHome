package iut.dam.tp1powerhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import iut.dam.tp1powerhome.entities.Appliance;

public class ApplianceAdapter extends RecyclerView.Adapter<ApplianceAdapter.ApplianceViewHolder> {

    private List<Appliance> appliances;
    private Context context;

    public ApplianceAdapter(Context context, List<Appliance> appliances) {
        this.context = context;
        this.appliances = appliances;
    }

    @NonNull
    @Override
    public ApplianceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 🔥 MODIF 1 : On charge TA nouvelle carte stylée (item_appliance.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.item_appliance, parent, false);
        return new ApplianceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplianceViewHolder holder, int position) {
        Appliance app = appliances.get(position);

        // On injecte les données de la BDD dans la carte
        holder.tvName.setText(app.getNom());
        holder.tvPower.setText(app.getPuissanceWatts() + " W");
    }

    @Override
    public int getItemCount() {
        return appliances == null ? 0 : appliances.size();
    }

    // C'est ici qu'on fait le lien avec les éléments de ton XML
    public static class ApplianceViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPower;

        public ApplianceViewHolder(@NonNull View itemView) {
            super(itemView);

            // 🔥 MODIF 2 : On utilise les VRAIS IDs qu'on a mis dans item_appliance.xml
            tvName = itemView.findViewById(R.id.tv_appliance_name);
            tvPower = itemView.findViewById(R.id.tv_appliance_power);
        }
    }
}