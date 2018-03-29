package com.example.javier.MaterialDesignApp.Tabs.TabsAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.javier.MaterialDesignApp.Tabs.TabsViews.TabFriendsStores;
import com.example.javier.MaterialDesignApp.Tabs.TabsViews.TabFavStores;
import com.example.javier.MaterialDesignApp.Tabs.TabsViews.TabStores;

import java.util.ArrayList;

public class TabsDesignViewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<CharSequence> titles;
    int tabNumber;

    // Constructor
    public TabsDesignViewPagerAdapter (FragmentManager fragmentManager, ArrayList<CharSequence> titles, int tabNumber) {
        super(fragmentManager);

        this.titles = titles;
        this.tabNumber = tabNumber;

    }

    // Return Fragment for each position
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:{
                TabStores tabStores = new TabStores();
                return  tabStores;
            }
            case 1:{
                TabFavStores tabFavStores = new TabFavStores();
                return  tabFavStores;
            }
            case 2:{
                TabFriendsStores friendsStores = new TabFriendsStores();
                return  friendsStores;
            }
        }
        return null;
    }

    // Return tab titles for each position

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    // Return tab number.
    @Override
    public int getCount() {
        return tabNumber;
    }
}