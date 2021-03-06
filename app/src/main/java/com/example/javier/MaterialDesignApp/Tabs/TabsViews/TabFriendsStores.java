package com.example.javier.MaterialDesignApp.Tabs.TabsViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.javier.MaterialDesignApp.DetailActivity;
import com.example.javier.MaterialDesignApp.R;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewAdapters.CheckinAdapter;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewAdapters.StoresAdapter;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewClasses.Checkin;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewClasses.Stores;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewDecorations.DividerItemDecoration;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewUtils.ItemClickSupport;
import com.example.javier.MaterialDesignApp.SharedPreference;
import com.example.javier.MaterialDesignApp.Splashscreen;
import com.example.javier.MaterialDesignApp.Tabs.TabsUtils.SlidingTabLayout;
import com.example.javier.MaterialDesignApp.Utils.JsonParser;
import com.example.javier.MaterialDesignApp.Utils.ScrollManagerToolbarTabs;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.ArrayList;

public class TabFriendsStores extends Fragment {

    String urlPost;
    JSONObject jsonObjectDesignPosts;
    ArrayList<Checkin> checkins=new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    View view;
    SharedPreferences sharedPreferences;
    Toolbar toolbar;
    SlidingTabLayout tabs;
    int recyclerViewPaddingTop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_design, container, false);

        // Get shared preferences
        sharedPreferences = getActivity().getSharedPreferences("VALUES", Context.MODE_PRIVATE);

        // Setup RecyclerView News
        recyclerViewDesign(view);

        // Setup swipe to refresh
        swipeToRefresh(view);

        return view;
    }

    private void recyclerViewDesign(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewDesign);
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);

        // Divider
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(android.R.drawable.divider_horizontal_bright)));

        // improve performance if you know that changes in content
        // do not change the size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        int id= sharedPreferences.getInt("USERID", 0);
        urlPost="http://"+Splashscreen.prefixe+".ngrok.io/beauty-shops/check_ins_friends.php?user1="+id;
        Log.d("resul",urlPost);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        new AsyncTaskNewsParseJson().execute(urlPost);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                sharedPreferences.edit().putString("str_name", checkins.get(position).getStores().getStr_name()).apply();
                sharedPreferences.edit().putString("str_long", checkins.get(position).getStores().getStr_long()).apply();
                sharedPreferences.edit().putString("str_lat", checkins.get(position).getStores().getStr_alt()).apply();
                sharedPreferences.edit().putString("str_phone", checkins.get(position).getStores().getStr_phone()).apply();

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                startActivity(intent);
            }
        });

    }

    public class AsyncTaskNewsParseJson extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
        }

        // get JSON Object
        @Override
        protected String doInBackground(String... url) {

            urlPost = url[0];
            try {
                jsonObjectDesignPosts = JsonParser.readJsonFromUrl(urlPost);
                Log.d("resul",jsonObjectDesignPosts.toString());
                int count = jsonObjectDesignPosts.getInt("count");
                Log.d("resul",Integer.toString(count));
                for (int i=1;i<=count;i++){
                    JSONObject resul = jsonObjectDesignPosts.getJSONObject(""+i);
                    Log.d("resul",resul.toString());
                    JSONObject store = resul.getJSONObject("store");
                    Log.d("resul",store.toString());
                    String str_id=store.getString("str_id");
                    String str_name=store.getString("str_name");
                    String str_phone=store.getString("str_phone");
                    String str_long=store.getString("str_long");
                    String str_alt=store.getString("str_alt");
                    String str_op_time=store.getString("str_op_time");
                    String str_cl_time=store.getString("str_cl_time");
                    String str_rate=store.getString("str_rate");
                    String str_nbr_rate=store.getString("str_nbr_rate");
                    String str_nbr_available=store.getString("str_nbr_available");
                    String str_user=store.getString("str_user");


                    int hour = resul.getInt("hour");
                    Log.d("resul",Integer.toString(hour));
                    int min = resul.getInt("min");

                    Stores s= new Stores(str_id,str_name,str_phone,str_long,str_alt,str_op_time,str_cl_time,str_rate,str_nbr_rate,str_nbr_available,str_user);

                    checkins.add(new Checkin(s,hour,min));
                }
                Log.d("resul","wa2");

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.d("resul","wa3");
            }
            return null;
        }

        // Set facebook items to the textviews and imageviews
        @Override
        protected void onPostExecute(String result) {
            Log.v("stores3",Integer.toString(checkins.size()));
            // Create the recyclerViewAdapter
            recyclerViewAdapter = new CheckinAdapter(getActivity(), checkins);
            recyclerView.setAdapter(recyclerViewAdapter);

            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
            swipeRefreshLayout.setRefreshing(false);

            // Create the recyclerViewAdapter
            recyclerViewAdapter = new CheckinAdapter(getActivity(), checkins);
            recyclerView.setAdapter(recyclerViewAdapter);

            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
            swipeRefreshLayout.setRefreshing(false);

            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void swipeToRefresh(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        int start = recyclerViewPaddingTop - convertToPx(48), end = recyclerViewPaddingTop + convertToPx(16);
        swipeRefreshLayout.setProgressViewOffset(true, start, end);
        TypedValue typedValueColorPrimary = new TypedValue();
        TypedValue typedValueColorAccent = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValueColorPrimary, true);
        getActivity().getTheme().resolveAttribute(R.attr.colorAccent, typedValueColorAccent, true);
        final int colorPrimary = typedValueColorPrimary.data, colorAccent = typedValueColorAccent.data;
        swipeRefreshLayout.setColorSchemeColors(colorPrimary, colorAccent);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkins=new ArrayList<>();
                new AsyncTaskNewsParseJson().execute(urlPost);
            }
        });
    }

    public int convertToPx(int dp) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }

    public void toolbarHideShow() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                ScrollManagerToolbarTabs manager = new ScrollManagerToolbarTabs(getActivity());
                manager.attach(recyclerView);
                manager.addView(toolbar, ScrollManagerToolbarTabs.Direction.UP);
                manager.setInitialOffset(toolbar.getHeight());
            }
        });
    }
}

