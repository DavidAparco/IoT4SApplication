package com.smartcity.iot4;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by root on 06/10/16.
 */
public class Utils {
    public static ArrayList<IoT4Device> getIoT4Sensors(){
        ArrayList<IoT4Device> devices = new ArrayList<>();
        devices.add(new IoT4Device("C1001", "ver1", "2016-05-09", "21:44:10",
                new LatLng(-77.04918, -12.016674), "CTIC",
                new String[]{"S50001","S51002","S52002","S53002","S54002","S55002","S56002","S57002","S58001"}));

        devices.add(new IoT4Device("C1002", "ver1", "2016-09-20", "13:44:10",
                new LatLng(-77.05001, -12.017082), "Ciencias",
                new String[]{"S50002","S51001","S52001","S53001","S54001","S55001","S56001","S57001","S58001"}));

        return devices;
    }

    public static ArrayList<Sensor> getSensorsNames(){
        ArrayList<Sensor> sensors = new ArrayList<>();
        sensors.add(new Sensor("Temperatura", "temperatura"));
        sensors.add(new Sensor("Presión", "presion"));
        sensors.add(new Sensor("Monóxido", "monoxido"));
        sensors.add(new Sensor("Dióxido", "dioxido"));
        sensors.add(new Sensor("Amoniaco", "amoniaco"));
        sensors.add(new Sensor("Altura", "altura"));
        sensors.add(new Sensor("Proximidad", "proximidad"));
        sensors.add(new Sensor("Viento", "viento"));
        sensors.add(new Sensor("Sonido", "sonido"));
        return sensors;
    }

    public static class Sensor {
        private String mName;
        private String mCodename;

        public Sensor(String name, String codename) {
            this.mName = name;
            this.mCodename = codename;
        }

        public String getName() {
            return mName;
        }

        public String getCodename() {
            return mCodename;
        }
    }
}
