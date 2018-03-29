package com.example.javier.MaterialDesignApp.Tabs.TabsViews;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.javier.MaterialDesignApp.R;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewAdapters.CommentsAdapter;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewAdapters.StoresAdapter;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewClasses.Comment;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewClasses.Stores;
import com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewDecorations.DividerItemDecoration;
import com.example.javier.MaterialDesignApp.Splashscreen;
import com.example.javier.MaterialDesignApp.Utils.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CommentsFragment extends Fragment {

    String urlPost;
    JSONObject jsonObjectDesignPosts;
    ArrayList<Comment> comments =new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    View view;
    SharedPreferences sharedPreferences;
    int recyclerViewPaddingTop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.comment, container, false);

        // Get shared preferences
        sharedPreferences = getActivity().getSharedPreferences("VALUES", Context.MODE_PRIVATE);

        // Setup RecyclerView News
        recyclerViewDesign(view);

        return view;
    }

    private void recyclerViewDesign(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewDesign);

        // Divider
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(android.R.drawable.divider_horizontal_bright)));

        // improve performance if you know that changes in content
        // do not change the size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        urlPost = "http://e1df263a.ngrok.io/beauty-shops/store_by_radius.php?radius=10&long="+ MainActivity.longitude+"&lat="+MainActivity.latitude;
//        urlPost = "http://e1df263a.ngrok.io/beauty-shops/stores.php";
        urlPost="http://"+Splashscreen.prefixe+".ngrok.io/beauty-shops/comments.php?store=1";

        new AsyncTaskNewsParseJson().execute(urlPost);

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
                int count = jsonObjectDesignPosts.getInt("count");
                for (int i=1;i<=count;i++){
                    JSONObject store = jsonObjectDesignPosts.getJSONObject(""+i);
                    int cmnt_id=store.getInt("cmnt_id");
                    String cmnt_str	=store.getString("cmnt_str");
                    int cmnt_user=store.getInt("cmnt_user");
                    int cmnt_store=store.getInt("cmnt_store");
                    int cmnt_hour=store.getInt("cmnt_hour");
                    int cmnt_min=store.getInt("cmnt_min");


                    comments.add(new Comment(cmnt_id,cmnt_str,cmnt_user,cmnt_store,cmnt_hour,cmnt_min));
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Set facebook items to the textviews and imageviews
        @Override
        protected void onPostExecute(String result) {
            Log.v("stores3",Integer.toString(comments.size()));
            // Create the recyclerViewAdapter
            recyclerViewAdapter = new CommentsAdapter(getActivity(), comments);
            recyclerView.setAdapter(recyclerViewAdapter);

            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
            swipeRefreshLayout.setRefreshing(false);

            // Create the recyclerViewAdapter
            recyclerViewAdapter = new CommentsAdapter(getActivity(), comments);
            recyclerView.setAdapter(recyclerViewAdapter);

            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
            swipeRefreshLayout.setRefreshing(false);

            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
        }
    }

    public int convertToPx(int dp) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }
}

