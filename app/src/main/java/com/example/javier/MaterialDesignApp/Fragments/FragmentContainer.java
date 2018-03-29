package com.example.javier.MaterialDesignApp.Fragments;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javier.MaterialDesignApp.MainActivity;
import com.example.javier.MaterialDesignApp.R;
import com.example.javier.MaterialDesignApp.Tabs.TabsAdapters.TabsDesignViewPagerAdapter;
import com.example.javier.MaterialDesignApp.Tabs.TabsUtils.SlidingTabLayout;

import java.util.ArrayList;

public class FragmentContainer extends Fragment {

    View view;
    ViewPager pager;
    TabsDesignViewPagerAdapter tabsDesignViewPagerAdapter;
    SlidingTabLayout tabs;
    public static ArrayList<CharSequence> titles=new ArrayList<>();
    int tabNumber = titles.size();
    int tabsPaddingTop;
    TypedValue typedValueToolbarHeight = new TypedValue();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_container, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Daily Life");

        if (titles.size()==0){
            titles.add("Nearby");
            titles.add("Favoris");
            titles.add("Friends");
        }

        //  Setup tabs
        setupTabs();

        return view;
    }

    public void setupTabs() {
        tabsDesignViewPagerAdapter = new TabsDesignViewPagerAdapter(getFragmentManager(), titles, 3);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(tabsDesignViewPagerAdapter);
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        // Tab indicator color
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.md_white_1000);
            }
        });

        // Setup tabs top padding
        getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValueToolbarHeight, true);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (Build.VERSION.SDK_INT >= 19) {
                tabsPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics()) + convertToPx(25);
            }else{
                tabsPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics());
            }
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (Build.VERSION.SDK_INT >= 21) {
                tabsPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics());
            }
            if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21){
                tabsPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics()) + convertToPx(25);
            }
            if (Build.VERSION.SDK_INT < 19) {
                tabsPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics());
            }
        }
        tabs.setPadding(convertToPx(48), tabsPaddingTop, convertToPx(16), 0);

        tabs.setViewPager(pager);
    }

    public int convertToPx(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}

