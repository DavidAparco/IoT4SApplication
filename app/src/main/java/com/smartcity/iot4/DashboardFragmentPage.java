package com.smartcity.iot4;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragmentPage extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String mSensorType;

    public DashboardFragmentPage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        mSensorType = DashboardFragment.getCodenameFragment(this);
        ArrayList<IoT4Device> items = null;

        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                items = getDevices(mSensorType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        View rootView = inflater.inflate(R.layout.fragment_dashboard_page, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        adapter = new DashboardPageAdapter(items, getActivity().getSupportFragmentManager(), getContext());
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    public ArrayList<IoT4Device> getDevices(String sensorType) throws IOException {
        final String URL = "http://52.10.199.174:8081/consInfoSensor/";
        InputStream is = null;
        JSONObject json_device;
        JSONObject json_sensor;
        char[] buffer = new char[2000];
        ArrayList<IoT4Device> items = new ArrayList();
        try {
            HttpURLConnection conn = abroConexion(URL + sensorType);
            is = conn.getInputStream();
            Reader reader = new InputStreamReader(is, "UTF-8");
            reader.read(buffer);
            JSONObject jsonObject = new JSONObject(new String(buffer));
            JSONArray jsonArray = jsonObject.getJSONArray("array_json");

            for (int i = 0; i < jsonArray.length(); ++i) {
                json_device = jsonArray.getJSONObject(i);
                json_sensor = jsonArray.getJSONObject(i).getJSONArray("sensores").getJSONObject(0);
                IoT4Device device = new IoT4Device(json_device.getString("id_cansat"),
                                            json_sensor.getString("id_sensor"),
                                            json_sensor.getString("modelo"),
                                            json_sensor.getString("tipo_sensor"),
                                            json_sensor.getString("unidad"),
                                            json_sensor.getString("f_install"),
                                            json_sensor.getString("h_install"));

                items.add(device);
            }

        } catch(JSONException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return items;
    }

    private HttpURLConnection abroConexion(String url) {
        HttpURLConnection conn = null;
        try {
            URL mUrl = new URL(url);
            conn = (HttpURLConnection) mUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
        } catch (IOException e){
            e.printStackTrace();
        }
        return conn;
    }

}
