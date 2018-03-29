package com.example.javier.MaterialDesignApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javier.MaterialDesignApp.Fragments.FragmentContainer;
import com.example.javier.MaterialDesignApp.Tabs.TabsViews.CommentsFragment;
import com.example.javier.MaterialDesignApp.Utils.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Calendar;


public class DetailActivity extends ActionBarActivity {

    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    int theme;
    FrameLayout statusBar;

    ProgressDialog progress;

    TextView str_name;
    TextView str_phone;
    Button getDirection;

    RatingBar ratingBar;

    ImageView c1,c2,c3,c4,c5;
    JSONObject jsonObjectChairs;
    JSONObject jsonObjectRating;
    JSONObject jsonObjectComment;
    JSONObject jsonObjectFav;

    String urlPost;

    int c;
    private int id;
    private String urlPost2;
    ImageView sendComment;
    ImageView addFav;
    TextView commentText;
    String urlPost3;
    String urlPost4;
    int res;
    ImageView checkIn;
    String urlPost5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //addListenerOnRatingBar();

        // Get shared preferences
        sharedPreferences = getSharedPreferences("VALUES", MODE_PRIVATE);

        // Select theme saved by user (always before setContentView)
        theme();

        // Set content to the view
        setContentView(R.layout.activity_detail);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //Setup Status Bar and Toolbar
        toolbarStatusBar();

        // Fix issues for each version and modes (check method at end of this file)
        navigationBarStatusBar();

        // Set elements

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getDirection = (Button) findViewById(R.id.getDirection);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q="+sharedPreferences.getString("str_long", "")+","+sharedPreferences.getString("str_lat", "")+" (" + sharedPreferences.getString("str_name", "") + ")"));
                startActivity(intent);
            }
        });

        id= Integer.parseInt(sharedPreferences.getString("str_id","").toString());

        str_name = (TextView) findViewById(R.id.name);
        str_name.setText(sharedPreferences.getString("str_name",""));

        str_phone = (TextView) findViewById(R.id.phoneNum);
        str_phone.setText("+216-"+sharedPreferences.getString("str_phone",""));

        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        float f = Float.parseFloat(sharedPreferences.getString("str_rate",""));
        ratingBar.setRating(f);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Log.v("rate1",String.valueOf(ratingBar.getRating()));
                String rate=String.valueOf(ratingBar.getRating());
                Log.v("rate2",rate);
                urlPost2="http://"+Splashscreen.prefixe+".ngrok.io/beauty-shops/updateRating.php?store="+id+"&rate="+rate;
                Log.v("rate1",urlPost2);

                new AsyncTaskNewsParseJson2().execute(urlPost2);
            }

        });


        c1= (ImageView) findViewById(R.id.c1);
        c2= (ImageView) findViewById(R.id.c2);
        c3= (ImageView) findViewById(R.id.c3);
        c4= (ImageView) findViewById(R.id.c4);
        c5= (ImageView) findViewById(R.id.c5);

        c= Integer.parseInt(sharedPreferences.getString("str_nbr_available","").toString());



        switch (c){
            case 1:
                c1.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                break;
            case 2:
                c1.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                c2.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                break;
            case 3:
                c1.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                c2.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                c3.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                break;
            case 4:
                c1.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                c2.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                c3.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                c4.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                break;
            case 5:
                c1.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                c2.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                c3.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                c4.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                c5.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                break;
        }

        urlPost="http://"+Splashscreen.prefixe+".ngrok.io/beauty-shops/bookChair.php?store="+id;

        Button chairBtn = (Button) findViewById(R.id.chairBtn);
        chairBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTaskNewsParseJson().execute(urlPost);
            }
        });

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        CommentsFragment commentsFragment = new CommentsFragment();
        fragmentTransaction.replace(R.id.fragment, commentsFragment);
        fragmentTransaction.commit();

        sendComment = (ImageView) findViewById(R.id.sendComment);
        addFav = (ImageView) findViewById(R.id.addFav);
        checkIn = (ImageView) findViewById(R.id.checkin);

        commentText=(TextView) findViewById(R.id.commentText);


        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int h = c.get(Calendar.HOUR);
                int m = c.get(Calendar.MINUTE);
                String commentTxt=commentText.getText().toString();
                commentTxt=commentTxt.replace(" ","%20");
                int user=sharedPreferences.getInt("USERID",0);
                String store=sharedPreferences.getString("str_id","");
                urlPost3="http://"+Splashscreen.prefixe+".ngrok.io/beauty-shops/addComment.php?cmnt="+commentTxt+"&user="+user+"&store="+store+"&hour="+h+"&min="+m;

                new AsyncTaskNewsParseJson3().execute(urlPost3);
            }
        });

        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int user=sharedPreferences.getInt("USERID",0);
                String str=sharedPreferences.getString("str_id","");
                urlPost4="http://"+Splashscreen.prefixe+".ngrok.io/beauty-shops/addFav.php?user="+user+"&store="+str;

                new AsyncTaskNewsParseJson4().execute(urlPost4);
            }
        });

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int h = c.get(Calendar.HOUR);
                int m = c.get(Calendar.MINUTE);
                int user=sharedPreferences.getInt("USERID",0);
                String store=sharedPreferences.getString("str_id","");
                urlPost5="http://"+Splashscreen.prefixe+".ngrok.io/beauty-shops/checkin.php?&user="+user+"&store="+store+"&hour="+h+"&min="+m;

                new AsyncTaskNewsParseJson5().execute(urlPost5);
            }
        });
    }

    public class AsyncTaskNewsParseJson extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            progress=new ProgressDialog(DetailActivity.this);
            progress.setMessage("Booking a chair...");
            progress.setProgressStyle(ProgressDialog.THEME_HOLO_LIGHT);
            progress.show();
        }

        // get JSON Object
        @Override
        protected String doInBackground(String... url) {

            urlPost = url[0];
            try {
                jsonObjectChairs = JsonParser.readJsonFromUrl(urlPost);
                JSONObject store = jsonObjectChairs.getJSONObject("nb");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Set facebook items to the textviews and imageviews
        @Override
        protected void onPostExecute(String result) {
            int chair = c++;
            switch (chair){
                case 1:
                    c1.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                    break;
                case 2:
                    c2.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                    break;
                case 3:
                    c3.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                    break;
                case 4:
                    c4.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                    break;
                case 5:
                    c5.setImageDrawable(getResources().getDrawable(R.drawable.redchair));
                    break;
            }
            c1.setImageDrawable(getResources().getDrawable(R.drawable.redchair));

            progress.dismiss();
        }
    }

    public class AsyncTaskNewsParseJson3 extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            progress=new ProgressDialog(DetailActivity.this);
            progress.setMessage("Add your comment");
            progress.setProgressStyle(ProgressDialog.THEME_HOLO_LIGHT);
            progress.show();
        }

        // get JSON Object
        @Override
        protected String doInBackground(String... url) {

            urlPost = url[0];
            try {
                Log.v("Comment",urlPost3);
                jsonObjectComment = JsonParser.readJsonFromUrl(urlPost3);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Set facebook items to the textviews and imageviews
        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
        }
    }

    public class AsyncTaskNewsParseJson4 extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            progress=new ProgressDialog(DetailActivity.this);
            progress.setMessage("Adding to favorites");
            progress.setProgressStyle(ProgressDialog.THEME_HOLO_LIGHT);
            progress.show();
        }

        // get JSON Object
        @Override
        protected String doInBackground(String... url) {

            urlPost = url[0];
            try {
                Log.v("Fav",urlPost4);
                jsonObjectFav = JsonParser.readJsonFromUrl(urlPost4);
                res= jsonObjectFav.getInt("result");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Set facebook items to the textviews and imageviews
        @Override
        protected void onPostExecute(String result) {
            if (res==0){
                Toast.makeText(getApplicationContext(), "Already in you favorite list",
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Successfully added",
                        Toast.LENGTH_LONG).show();
            }
            progress.dismiss();
        }
    }

    public class AsyncTaskNewsParseJson5 extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            progress=new ProgressDialog(DetailActivity.this);
            progress.setMessage("Checking in...");
            progress.setProgressStyle(ProgressDialog.THEME_HOLO_LIGHT);
            progress.show();
        }

        // get JSON Object
        @Override
        protected String doInBackground(String... url) {

            urlPost5 = url[0];
            try {
                Log.v("Fav",urlPost5);
                jsonObjectFav = JsonParser.readJsonFromUrl(urlPost5);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Set facebook items to the textviews and imageviews
        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();

            Toast.makeText(getApplicationContext(), "Checked",
                    Toast.LENGTH_LONG).show();
        }
    }

    public class AsyncTaskNewsParseJson2 extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            progress=new ProgressDialog(DetailActivity.this);
            progress.setMessage("Rating");
            progress.setProgressStyle(ProgressDialog.THEME_HOLO_LIGHT);
            progress.show();
        }

        // get JSON Object
        @Override
        protected String doInBackground(String... url) {

            urlPost2 = url[0];
            try {
                jsonObjectRating = JsonParser.readJsonFromUrl(urlPost2);
                JSONObject store = jsonObjectRating.getJSONObject("update");
                Log.v("rate",store.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Set facebook items to the textviews and imageviews
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(),"Thank you for rating",Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void theme() {
        sharedPreferences = getSharedPreferences("VALUES", Context.MODE_PRIVATE);
        theme = sharedPreferences.getInt("THEME", 0);
        settingTheme(theme);
    }

    public void toolbarStatusBar() {

        // Cast toolbar and status bar
        statusBar = (FrameLayout) findViewById(R.id.statusBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Get support to the toolbar and change its title
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void navigationBarStatusBar() {

        // Fix portrait issues
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Fix issues for KitKat setting Status Bar color primary
            if (Build.VERSION.SDK_INT >= 19) {
                TypedValue typedValue19 = new TypedValue();
                DetailActivity.this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
                final int color = typedValue19.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
            }

            // Fix issues for Lollipop, setting Status Bar color primary dark
            if (Build.VERSION.SDK_INT >= 21) {
                TypedValue typedValue21 = new TypedValue();
                DetailActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue21, true);
                final int color = typedValue21.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
                getWindow().setStatusBarColor(color);
            }
        }

        // Fix landscape issues (only Lollipop)
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (Build.VERSION.SDK_INT >= 19) {
                TypedValue typedValue19 = new TypedValue();
                DetailActivity.this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
                final int color = typedValue19.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                TypedValue typedValue = new TypedValue();
                DetailActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
                final int color = typedValue.data;
                getWindow().setStatusBarColor(color);
            }
        }
    }

    public void settingTheme(int theme) {
        switch (theme) {
            case 1:
                setTheme(R.style.AppTheme);
                break;
            case 2:
                setTheme(R.style.AppTheme2);
                break;
            case 3:
                setTheme(R.style.AppTheme3);
                break;
            case 4:
                setTheme(R.style.AppTheme4);
                break;
            case 5:
                setTheme(R.style.AppTheme5);
                break;
            case 6:
                setTheme(R.style.AppTheme6);
                break;
            case 7:
                setTheme(R.style.AppTheme7);
                break;
            case 8:
                setTheme(R.style.AppTheme8);
                break;
            case 9:
                setTheme(R.style.AppTheme9);
                break;
            case 10:
                setTheme(R.style.AppTheme10);
                break;
            default:
                setTheme(R.style.AppTheme);
                break;
        }
    }

}
