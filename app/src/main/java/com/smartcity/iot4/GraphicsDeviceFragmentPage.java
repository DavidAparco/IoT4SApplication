package com.smartcity.iot4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.smartcity.iot4.Utils.getSensorsNames;

public class GraphicsDeviceFragmentPage extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Spinner mSpinner;
    private int mIndicator = -1;
    private static final String GET_INDICATOR = "get_indicator";
    List<String> elements;
    private String[] zonas = new String[] {"Zona 1", "Zona 2", "Zona 3", "Zona 4"};
    private String[] sensorNames;
    private String mSensorType;

    public GraphicsDeviceFragmentPage() {
    }

    public static Fragment newInstanceGDFP(int indicator) {
        Fragment fragment = new GraphicsDeviceFragmentPage();
        Bundle bundle = new Bundle();
        bundle.putInt(GET_INDICATOR, indicator);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.getArguments() != null) {
            Bundle bundle = this.getArguments();
            mIndicator = bundle.getInt(GET_INDICATOR);
        }
        ArrayList<Utils.Sensor> sensors = getSensorsNames();
        sensorNames = new String[sensors.size()];
        for (int ii = 0; ii < sensors.size(); ++ii) {
            sensorNames[ii] = sensors.get(ii).getName();
        }
        View rootView = inflater.inflate(R.layout.fragment_graphics_page, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_graphics);
        mSpinner = (Spinner) rootView.findViewById(R.id.graphics_spinner);
        if (mIndicator == 0) {
            elements = new ArrayList<String>(Arrays.asList(zonas));
        } else if (mIndicator == 1) {
            elements = new ArrayList<String>(Arrays.asList(sensorNames));
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, elements);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);

        //layoutManager = new LinearLayoutManager(getActivity());
        layoutManager = new GridLayoutManager(getActivity(), 2);

        ArrayList<IoT4Device> items = new ArrayList();
        items.add(new IoT4Device("C1001", "ver1", "2016-05-09", "21:44:10",
                new LatLng(-77.04918, -12.016674), "CTIC",
                new String[]{"S50001", "S51002", "S52002", "S53002", "S54002", "S55002", "S56002", "S57002", "S58001"}));
        items.add(new IoT4Device("C1002", "ver1", "2016-05-09", "21:44:10",
                new LatLng(-77.04918, -12.016674), "CTIC",
                new String[]{"S60001", "S61002", "S62002", "S63002", "S64002", "S65002", "S66002", "S67002", "S68001"}));
        items.add(new IoT4Device("C1003", "ver1", "2016-05-09", "21:44:10",
                new LatLng(-77.04918, -12.016674), "Facultad de Ciencias",
                new String[]{"S70001", "S71002", "S72002", "S73002", "S74002", "S75002", "S76002", "S77002", "S78001"}));
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GraphicsPageAdapter(items, getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}