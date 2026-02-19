package iut.dam.tp1powerhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import iut.dam.tp1powerhome.appliance.IAppliance;

public class HabitatAdapter extends ArrayAdapter<Habitat> {

    public HabitatAdapter(@NonNull Context context, @NonNull List<Habitat> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Habitat habitat = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_habitat, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tv_resident_name);
        TextView tvCount = convertView.findViewById(R.id.tv_appliances_count);
        TextView tvFloorNum = convertView.findViewById(R.id.tv_floor_number);
        LinearLayout iconsContainer = convertView.findViewById(R.id.ll_icons_container);

        if (habitat != null) {
            tvName.setText(habitat.getResidentName());

            // Gestion Pluriels Internationalisée
            int count = habitat.getAppliances().size();
            String countLabel;
            if (count == 0) {
                countLabel = getContext().getString(R.string.no_appliance);
            } else if (count == 1) {
                countLabel = getContext().getString(R.string.appliance_count_singular, count);
            } else {
                countLabel = getContext().getString(R.string.appliance_count_plural, count);
            }
            tvCount.setText(countLabel);

            // Gestion RDC Internationalisée
            if (habitat.getFloor() == 0) {
                tvFloorNum.setText(getContext().getString(R.string.floor_rdc));
                tvFloorNum.setTextSize(12);
            } else {
                tvFloorNum.setText(String.valueOf(habitat.getFloor()));
                tvFloorNum.setTextSize(16);
            }

            // Limitation Icônes +X
            iconsContainer.removeAllViews();
            List<IAppliance> apps = habitat.getAppliances();
            int maxIcons = 7;

            for (int i = 0; i < apps.size(); i++) {
                if (i < maxIcons) {
                    ImageView iv = new ImageView(getContext());
                    int size = (int) (18 * getContext().getResources().getDisplayMetrics().density);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
                    params.setMargins(2, 0, 2, 0);
                    iv.setLayoutParams(params);
                    iv.setImageResource(apps.get(i).getIconResId());
                    iv.setColorFilter(0xFF777777);
                    iconsContainer.addView(iv);
                } else {
                    TextView tvMore = new TextView(getContext());
                    tvMore.setText("+" + (apps.size() - maxIcons));
                    tvMore.setTextSize(11);
                    tvMore.setTextColor(0xFF777777);
                    tvMore.setPadding(4, 0, 0, 0);
                    iconsContainer.addView(tvMore);
                    break;
                }
            }
        }
        return convertView;
    }
}