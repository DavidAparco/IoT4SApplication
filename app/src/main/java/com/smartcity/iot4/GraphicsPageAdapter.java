package com.smartcity.iot4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GraphicsPageAdapter extends RecyclerView.Adapter<GraphicsPageAdapter.DeviceViewHolder> {

    private static ArrayList<IoT4Device> items;
    private static FragmentManager fm;
    public static final String DEVICE_INFO = "device_info";
    public static int currentPosition;

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView modelo;
        public TextView installDate;
        public TextView installHour;
        public TextView zona;
        public CardView cardview;

        //String id, String modelo, String installDate, String installHour, LatLng position, String zona, String[] sensorsAttached;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.deviceId);
            modelo = (TextView) itemView.findViewById(R.id.modelo);
            installDate = (TextView) itemView.findViewById(R.id.installDate);
            installHour = (TextView) itemView.findViewById(R.id.installHour);
            zona = (TextView) itemView.findViewById(R.id.zona);
            cardview = (CardView) itemView.findViewById(R.id.cardview_graphics);
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new ChartsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(DEVICE_INFO, items.get(getAdapterPosition()));
                    fragment.setArguments(bundle);
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame_contenido, fragment)
                            .commit();
                }
            });
        }
    }

    public GraphicsPageAdapter(ArrayList<IoT4Device> items, FragmentManager fm) {
        this.items = items;
        this.fm = fm;
    }

    @Override
    public GraphicsPageAdapter.DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_graphics_page_item, parent, false);
        return new GraphicsPageAdapter.DeviceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GraphicsPageAdapter.DeviceViewHolder holder, int position) {
        //holder.imagen.setImageResource(items.get(position).getImagen());
        currentPosition = position;
        holder.id.setText(items.get(position).getId());
        holder.modelo.setText("Modelo: " + items.get(position).getModelo());
        holder.installDate.setText("Fecha de Instalación: " + items.get(position).getInstallDate());
        holder.installHour.setText("Hora de Instalación: " + items.get(position).getInstallHour());
        holder.zona.setText("Zona: " + items.get(position).getZona());
        /*holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = new ChartsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(DEVICE_INFO, items.get(currentPosition));
                fragment.setArguments(bundle);
                ft.replace(R.id.frame_contenido, fragment)
                        .commit();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

