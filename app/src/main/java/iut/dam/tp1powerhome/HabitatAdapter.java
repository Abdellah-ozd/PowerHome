package iut.dam.tp1powerhome; // ⚠️ Vérifie que c'est bien le nom de TON package

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

// L'erreur "cannot be applied" vient si tu oublies "extends ArrayAdapter<Habitat>"
public class HabitatAdapter extends ArrayAdapter<Habitat> {

    // L'erreur "Default constructor" vient si tu oublies ce bloc :
    public HabitatAdapter(Context context, List<Habitat> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Habitat habitat = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_habitat, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tv_resident_name);
        TextView tvCount = convertView.findViewById(R.id.tv_appliances_count);
        TextView tvFloor = convertView.findViewById(R.id.tv_floor);

        if (habitat != null) {
            tvName.setText(habitat.getResidentName());
            tvCount.setText(habitat.getApplianceCountLabel());
            tvFloor.setText(habitat.getFloorLabel());
        }

        return convertView;
    }
}