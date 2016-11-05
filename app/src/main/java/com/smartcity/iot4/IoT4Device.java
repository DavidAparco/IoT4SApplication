package com.smartcity.iot4;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 06/10/16.
 */
public class IoT4Device implements Parcelable {
    String id;
    String id_sensor;
    String modelo;
    String tipo_sensor;
    String unidad;
    String installDate;
    String installHour;
    LatLng position;
    String zona;
    String[] sensorsAttached;

    public IoT4Device(String id, String modelo, String installDate, String installHour, LatLng position, String zona, String[] sensorsAttached) {
        this.id = id;
        this.modelo = modelo;
        this.installDate = installDate;
        this.installHour = installHour;
        this.position = position;
        this.zona = zona;
        this.sensorsAttached = sensorsAttached;
    }

    public IoT4Device(String id, String id_sensor, String modelo, String tipo_sensor, String unidad, String installDate, String installHour) {
        this.id = id;
        this.id_sensor = id_sensor;
        this.modelo = modelo;
        this.tipo_sensor = tipo_sensor;
        this.unidad = unidad;
        this.installDate = installDate;
        this.installHour = installHour;
    }

    protected IoT4Device(Parcel in) {
        id = in.readString();
        modelo = in.readString();
        installDate = in.readString();
        installHour = in.readString();
        position = in.readParcelable(LatLng.class.getClassLoader());
        zona = in.readString();
        sensorsAttached = in.createStringArray();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(modelo);
        out.writeString(installDate);
        out.writeString(installHour);
        out.writeParcelable(position, flags);
        out.writeString(zona);
        out.writeStringArray(sensorsAttached);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IoT4Device> CREATOR = new Creator<IoT4Device>() {
        @Override
        public IoT4Device createFromParcel(Parcel in) {
            return new IoT4Device(in);
        }

        @Override
        public IoT4Device[] newArray(int size) {
            return new IoT4Device[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public String getInstallDate() {
        return installDate;
    }

    public String getInstallHour() {
        return installHour;
    }

    public String getZona() {
        return zona;
    }

    public String getSensorId() { return id_sensor; }

    public String getTipo() { return tipo_sensor; }

    public String getUnidad() { return unidad; }
}
