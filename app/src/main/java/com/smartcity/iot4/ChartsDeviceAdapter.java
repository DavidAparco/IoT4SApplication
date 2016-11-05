package com.smartcity.iot4;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ChartsDeviceAdapter extends RecyclerView.Adapter<ChartsDeviceAdapter.DeviceViewHolder> {

    private ArrayList<IoT4Device> items;
    private FragmentManager fm;

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView modelo;
        public TextView installDate;
        public TextView installHour;
        public TextView zona;

        //String id, String modelo, String installDate, String installHour, LatLng position, String zona, String[] sensorsAttached;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.deviceId_charts);
            modelo = (TextView) itemView.findViewById(R.id.modelo_charts);
            installDate = (TextView) itemView.findViewById(R.id.installDate_charts);
            installHour = (TextView) itemView.findViewById(R.id.installHour_charts);
            zona = (TextView) itemView.findViewById(R.id.zona_charts);
        }
    }

    public ChartsDeviceAdapter(ArrayList<IoT4Device> items, FragmentManager fm) {
        this.items = items;
        this.fm = fm;
    }

    @Override
    public ChartsDeviceAdapter.DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_charts_item, parent, false);
        return new ChartsDeviceAdapter.DeviceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChartsDeviceAdapter.DeviceViewHolder holder, int position) {
        //holder.imagen.setImageResource(items.get(position).getImagen());
        holder.id.setText(items.get(position).getId());
        holder.modelo.setText("Modelo: " + items.get(position).getModelo());
        holder.installDate.setText("Fecha de Instalación: " + items.get(position).getInstallDate());
        holder.installHour.setText("Hora de Instalación: " + items.get(position).getInstallHour());
        holder.zona.setText("Zona: " + items.get(position).getZona());
        /*holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_contenido, new ChartsFragment())
                        .commit();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

