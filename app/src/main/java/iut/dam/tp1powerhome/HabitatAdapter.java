package iut.dam.tp1powerhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import iut.dam.tp1powerhome.entities.Habitat;
import iut.dam.tp1powerhome.entities.Appliance;

public class HabitatAdapter extends RecyclerView.Adapter<HabitatAdapter.HabitatViewHolder> {

    private List<Habitat> habitats;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Habitat habitat);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public HabitatAdapter(Context context, List<Habitat> habitats) {
        this.context = context;
        this.habitats = habitats;
    }

    @NonNull
    @Override
    public HabitatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_habitat, parent, false);
        return new HabitatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitatViewHolder holder, int position) {
        Habitat habitat = habitats.get(position);

        if (habitat != null) {
            holder.tvName.setText(habitat.getResidentName() != null ? habitat.getResidentName() : "Inconnu");

            int count = habitat.getAppliances().size();
            String countLabel;
            if (count == 0) {
                countLabel = context.getString(R.string.no_appliance);
            } else if (count == 1) {
                countLabel = context.getString(R.string.appliance_count_singular, count);
            } else {
                countLabel = context.getString(R.string.appliance_count_plural, count);
            }
            holder.tvCount.setText(countLabel);

            if (habitat.getFloor() == 0) {
                holder.tvFloorNum.setText(context.getString(R.string.floor_rdc));
                holder.tvFloorNum.setTextSize(12);
            } else {
                holder.tvFloorNum.setText(String.valueOf(habitat.getFloor()));
                holder.tvFloorNum.setTextSize(16);
            }

            holder.iconsContainer.removeAllViews();
            List<Appliance> apps = habitat.getAppliances();
            int maxIcons = 5;

            for (int i = 0; i < apps.size(); i++) {
                if (i < maxIcons) {
                    ImageView iv = new ImageView(context);
                    int size = (int) (18 * context.getResources().getDisplayMetrics().density);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
                    params.setMargins(2, 0, 2, 0);
                    iv.setLayoutParams(params);
                    iv.setImageResource(apps.get(i).getIconResId());
                    iv.setColorFilter(0xFF777777);
                    holder.iconsContainer.addView(iv);
                } else {
                    TextView tvMore = new TextView(context);
                    tvMore.setText("+" + (apps.size() - maxIcons));
                    tvMore.setTextSize(11);
                    tvMore.setTextColor(0xFF777777);
                    tvMore.setPadding(4, 0, 0, 0);
                    holder.iconsContainer.addView(tvMore);
                    break;
                }
            }

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(habitat);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return habitats == null ? 0 : habitats.size();
    }

    public static class HabitatViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCount, tvFloorNum;
        LinearLayout iconsContainer;

        public HabitatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_resident_name);
            tvCount = itemView.findViewById(R.id.tv_appliances_count);
            tvFloorNum = itemView.findViewById(R.id.tv_floor_number);
            iconsContainer = itemView.findViewById(R.id.ll_icons_container);
        }
    }
}