package iut.dam.tp1powerhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        // On réutilise le layout des items de la dialog box ou un truc simple
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ApplianceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplianceViewHolder holder, int position) {
        Appliance app = appliances.get(position);
        holder.tvName.setText(app.getNom());
        holder.tvPower.setText(app.getPuissanceWatts() + " W");
        // On peut même mettre l'icône à gauche !
        holder.imgIcon.setImageResource(app.getIconResId());
    }

    @Override
    public int getItemCount() { return appliances == null ? 0 : appliances.size(); }

    public static class ApplianceViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPower;
        ImageView imgIcon;
        public ApplianceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(android.R.id.text1);
            tvPower = itemView.findViewById(android.R.id.text2);
            // On l'ajoute dynamiquement si on utilise un layout simple
            imgIcon = new ImageView(itemView.getContext());
        }
    }
}