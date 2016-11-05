package com.smartcity.iot4;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static com.smartcity.iot4.Utils.getIoT4Sensors;
import static com.smartcity.iot4.Utils.getSensorsNames;

public class DashboardFragment extends Fragment {

    private AppBarLayout appBar;
    private TabLayout tabs;
    private ViewPager viewPager;
    private static String SENSOR_TYPE = "sensor_type";

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        if (savedInstanceState == null) {
            insertTabs(container);
            viewPager = (ViewPager) rootView.findViewById(R.id.pager);
            populateViewPager(viewPager);
            tabs.setupWithViewPager(viewPager);
        }
        // Inflate the layout for this fragment
        return rootView;
    }

    private void insertTabs(ViewGroup container) {
        View parent = (View) container.getParent();
        appBar = (AppBarLayout) parent.findViewById(R.id.appbar);
        tabs = new TabLayout(getActivity());
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        appBar.addView(tabs);
    }

    private void populateViewPager(ViewPager viewPager) {
        AdaptadorSecciones adapter = new AdaptadorSecciones(getFragmentManager());
        ArrayList<Utils.Sensor> sensors = getSensorsNames();
        Fragment fragmenth;
        Bundle bundle;
        for (int i = 0; i < sensors.size(); ++i) {
            fragmenth = new DashboardFragmentPage();
            bundle = new Bundle();
            bundle.putString(SENSOR_TYPE, sensors.get(i).getCodename());
            fragmenth.setArguments(bundle);
            adapter.addFragment(fragmenth, sensors.get(i).getName());
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (tabs != null)
            appBar.removeView(tabs);
    }

    public static String getCodenameFragment(Fragment fragment) {
        return fragment.getArguments().getString(SENSOR_TYPE);
    }

    public class AdaptadorSecciones extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentos = new ArrayList<>();
        private final List<String> titulosFragmentos = new ArrayList<>();

        public AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            fragmentos.add(fragment);
            titulosFragmentos.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titulosFragmentos.get(position);
        }
    }

}
