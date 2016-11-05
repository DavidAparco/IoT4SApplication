package com.smartcity.iot4;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DashboardPageAdapter extends RecyclerView.Adapter<DashboardPageAdapter.DeviceViewHolder> {

    private static ArrayList<IoT4Device> items;
    private static FragmentManager fm;
    private Context context;
    public int currentPosition;
    public static final String DEVICE_INFO = "device_info";

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView id_sensor;
        public TextView modelo;
        public TextView tipo_sensor;
        public TextView unidad;
        public TextView installDate;
        public TextView installHour;
        public TextView zona;
        public Button mButton;
        public CardView cardview;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.deviceId);
            id_sensor = (TextView) itemView.findViewById(R.id.sensorId);
            modelo = (TextView) itemView.findViewById(R.id.modelo);
            tipo_sensor = (TextView) itemView.findViewById(R.id.tipoSensor);
            unidad = (TextView) itemView.findViewById(R.id.unidad);
            installDate = (TextView) itemView.findViewById(R.id.installDate);
            installHour = (TextView) itemView.findViewById(R.id.installHour);
            mButton = (Button) itemView.findViewById(R.id.buttonDash);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            cardview = (CardView) itemView.findViewById(R.id.cardview_dashboard);
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

    public DashboardPageAdapter(ArrayList<IoT4Device> items, FragmentManager fm, Context context) {
        this.items = items;
        this.fm = fm;
        this.context = context;
    }

    @Override
    public DashboardPageAdapter.DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_dashboard_page_item, parent, false);
        return new DeviceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DashboardPageAdapter.DeviceViewHolder holder, int position) {
        currentPosition = position;
        holder.id.setText(items.get(position).getId());
        holder.id_sensor.setText("Sensor Id: " + items.get(position).getSensorId());
        holder.modelo.setText("Modelo: " + items.get(position).getModelo());
        holder.tipo_sensor.setText("Tipo: " + items.get(position).getTipo());
        holder.unidad.setText("Unidad: " + items.get(position).getUnidad());
        holder.installDate.setText("Fecha de Instalación: " + items.get(position).getInstallDate());
        holder.installHour.setText("Hora de Instalación: " + items.get(position).getInstallHour());
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame_contenido, MapaFragment.newInstance("marker"))
                    .commit();
            }
        });
    }

    public static Parcelable getParcelableIoT4(Bundle bundle) {
        return bundle.getParcelable(DEVICE_INFO);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}