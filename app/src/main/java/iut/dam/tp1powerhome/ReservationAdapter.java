package iut.dam.tp1powerhome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import iut.dam.tp1powerhome.entities.Reservation;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {
    private List<Reservation> reservations;

    public ReservationAdapter(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation res = reservations.get(position);
        holder.tvDateHeure.setText(res.date_res + " à " + res.heure_res + "h00");
        holder.tvPuissance.setText(res.puissance_watts + " W");
    }

    @Override
    public int getItemCount() {
        return reservations != null ? reservations.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDateHeure, tvPuissance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateHeure = itemView.findViewById(R.id.tvDateHeure);
            tvPuissance = itemView.findViewById(R.id.tvPuissanceRes);
        }
    }
}