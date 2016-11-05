package com.smartcity.iot4;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GraphicsFragment extends Fragment {

    private AppBarLayout appBar;
    private TabLayout tabs;
    private ViewPager viewPager;
    private LayoutInflater inflater;

    public GraphicsFragment() {
        // Required empty public constructor
    }

    public static GraphicsFragment newInstance(String param1, String param2) {
        GraphicsFragment fragment = new GraphicsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graphics, container, false);
        if (savedInstanceState == null) {
            insertTabs(container);
            viewPager = (ViewPager) rootView.findViewById(R.id.pager_graphics);
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
        tabs.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        appBar.addView(tabs);
    }

    private void populateViewPager(ViewPager viewPager) {
        GraphicsFragment.AdaptadorSeccionesGraphics adapter = new GraphicsFragment.AdaptadorSeccionesGraphics(getFragmentManager());
        Fragment fragment = GraphicsDeviceFragmentPage.newInstanceGDFP(0);
        adapter.addFragment(fragment, "IoT4d");
        fragment = GraphicsDeviceFragmentPage.newInstanceGDFP(1);
        adapter.addFragment(fragment, "Sensores");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (tabs != null)
            appBar.removeView(tabs);
    }

    public class AdaptadorSeccionesGraphics extends FragmentStatePagerAdapter {
        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentTitles = new ArrayList<>();

        public AdaptadorSeccionesGraphics(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }

}
