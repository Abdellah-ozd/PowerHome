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

        // 1. Récupération des vues
        TextView tvName = convertView.findViewById(R.id.tv_resident_name);
        TextView tvCount = convertView.findViewById(R.id.tv_appliances_count);
        TextView tvFloorNum = convertView.findViewById(R.id.tv_floor_number);
        LinearLayout iconsContainer = convertView.findViewById(R.id.ll_icons_container);

        if (habitat != null) {
            // 2. Textes
            tvName.setText(habitat.getResidentName());
            tvCount.setText(habitat.getApplianceCountLabel());
            tvFloorNum.setText(habitat.getFloorNumber()); // Affiche juste "1"

            // 3. LES ICÔNES (Version Simplifiée) ⚙️⚙️⚙️

            // On vide le conteneur avant de le remplir (très important !)
            iconsContainer.removeAllViews();

            // Pour chaque équipement dans la liste, on ajoute une icône générique
            for (int i = 0; i < habitat.getAppliances().size(); i++) {
                ImageView iv = new ImageView(getContext());

                // Taille de l'icône (20dp)
                int size = (int) (20 * getContext().getResources().getDisplayMetrics().density);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
                params.setMargins(4, 0, 4, 0); // Espace entre les icônes
                iv.setLayoutParams(params);

                // Icône générique pour tout le monde (engrenage)
                iv.setImageResource(android.R.drawable.ic_menu_manage);
                iv.setColorFilter(0xFF555555); // Gris foncé

                // On l'ajoute à la ligne
                iconsContainer.addView(iv);
            }
        }

        return convertView;
    }
}