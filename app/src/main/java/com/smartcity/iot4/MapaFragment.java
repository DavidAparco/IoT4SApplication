package com.smartcity.iot4;


import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import android.Manifest;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static com.smartcity.iot4.Utils.getIoT4Sensors;
import static com.smartcity.iot4.Utils.getSensorsNames;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapaFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "tipo";
    private GoogleMap mMap;
    private MapView mapView;
    private Spinner spinner;
    List<String> elements;
    private String[] sensorNames;

    // TODO: Rename and change types of parameters
    private String tipoMapa;


    public MapaFragment() {
        // Required empty public constructor
    }

    public static MapaFragment newInstance(String tipoMapa) {
        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, tipoMapa);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tipoMapa = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        spinner = (Spinner) view.findViewById(R.id.marker_spinner);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        try{
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<Utils.Sensor> sensors = getSensorsNames();
        sensorNames = new String[sensors.size()];
        for (int ii = 0; ii < sensors.size(); ++ii) {
            sensorNames[ii] = sensors.get(ii).getName();
        }
        elements = new ArrayList<String>(Arrays.asList(sensorNames));
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, elements);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                } else {
                    // Show rationale and request permission.
                }

                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker arg0) {
                        View v = getActivity().getLayoutInflater().inflate(R.layout.info_window_layout, null);

                        TextView id = (TextView) v.findViewById(R.id.iwId);
                        TextView modelo = (TextView) v.findViewById(R.id.iwModelo);
                        TextView fechaIns = (TextView) v.findViewById(R.id.iwFechaI);
                        TextView horaIns = (TextView) v.findViewById(R.id.iwHoraI);
                        TextView zona = (TextView) v.findViewById(R.id.iwZona);
                        id.setText("C1001");
                        modelo.setText("Modelo: ver1");
                        fechaIns.setText("Fecha Instalación: 2016-05-09");
                        horaIns.setText("Hora Instalación: 21:44:10");
                        zona.setText("Zona: CTIC");
                        return v;

                    }
                });

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frame_contenido, new ChartsFragment())
                                .commit();
                    }
                });

                if (tipoMapa.equalsIgnoreCase("marker")){
                    LatLngBounds uniBounds = new LatLngBounds(
                            new LatLng(-12.02, -77.04),
                            new LatLng(-12.01, -77.05)
                    );

                    LatLng CTIC1 = new LatLng(-12.0166427,-77.0497901);

                    Marker mCTIC1 = mMap.addMarker(new MarkerOptions()
                            .position(CTIC1));

                    //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(uniBounds,0));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CTIC1, 15));
                }
                else if(tipoMapa.equalsIgnoreCase("heat")){
                    setUpMapIfNeeded(googleMap);
                    addHeatMap();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-37, 144), 10));
                    mMap.getUiSettings().setZoomGesturesEnabled(false);
                    /*LatLng CTIC1 = new LatLng(-12.0166427,-77.0497901);
                    CircleOptions circleOptions = new CircleOptions()
                            .center(CTIC1)   //set center
                            .radius(500)   //set radius in meters
                            .fillColor(0x50ff0000)  //default
                            .strokeColor(0x50ff0000)
                            .strokeWidth(5);
                    mMap.addCircle(circleOptions);

                    LatLng CTIC2 = new LatLng(-12.0196427,-77.097901);
                    CircleOptions circleOptions1 = new CircleOptions()
                            .center(CTIC2)   //set center
                            .radius(200)   //set radius in meters
                            .fillColor(0x50ff0000)  //default
                            .strokeColor(0x50ff0000)
                            .strokeWidth(5);
                    mMap.addCircle(circleOptions1);

                    LatLng CTIC3 = new LatLng(-12.019345,-77.0493453);
                    CircleOptions circleOptions2 = new CircleOptions()
                            .center(CTIC3)   //set center
                            .radius(300)   //set radius in meters
                            .fillColor(0x50ff0000)  //default
                            .strokeColor(0x50ff0000)
                            .strokeWidth(5);
                    mMap.addCircle(circleOptions2);

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CTIC1, 15));*/
                }
            }
        });

        return view;
    }

    private void setUpMapIfNeeded(GoogleMap googleMap) {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = googleMap;
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                // Position the camera
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-25, 135), 3));
            }
        }
    }

    private void addHeatMap() {
        List<WeightedLatLng> list = null;

        // Get the data: latitude/longitude positions of police stations.
        try {
            list = readItems(R.raw.police_stations);
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Problem reading list of locations.", Toast.LENGTH_LONG).show();
        }

        // Create a heat map tile provider, passing it the latlngs of the police stations.
        HeatmapTileProvider provider = new HeatmapTileProvider.Builder().weightedData(list).build();
        // Add a tile overlay to the map, using the heat map tile provider.
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
    }

    private ArrayList<WeightedLatLng> readItems(int resource) throws JSONException {
        ArrayList<WeightedLatLng> list = new ArrayList<WeightedLatLng>();
        InputStream inputStream = getResources().openRawResource(resource);
        @SuppressWarnings("resource")
        Random randomGenerator = new Random();
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            list.add(new WeightedLatLng(
                new LatLng(lat, lng),
                randomGenerator.nextInt(20)
            ));
        }

        return list;
    }
}
