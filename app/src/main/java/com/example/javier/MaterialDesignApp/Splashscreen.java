package com.example.javier.MaterialDesignApp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.javier.MaterialDesignApp.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Splashscreen extends Activity {

    public static String prefixe="fe7e9c65";
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;


    public LocationManager locationManager;
    public LocationListener locationListener;
    public static double latitude=0.0;
    public static double longitude=0.0;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("VALUES", MODE_PRIVATE);



        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude =location.getLatitude();
                longitude=location.getLongitude();
                Log.v("stores",Double.toString(latitude)+","+Double.toString(longitude));
                locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent=new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        //Toast.makeText(Splashscreen.this, Double.toString(latitude),Toast.LENGTH_LONG).show();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,30000,0,locationListener);


        setContentView(R.layout.activity_splashscreen);


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                        sleep(4000);
                    try {
                        Integer.toString(sharedPreferences.getInt("USERID", 0));
                            Intent myIntent = new Intent(Splashscreen.this, MainActivity.class);
                            Splashscreen.this.startActivity(myIntent);
                    }
                    catch (Exception e){
                        Log.d("sharedPreferences error","Can't get USERID");
                        Intent myIntent = new Intent(Splashscreen.this, LoginActivity.class);
                        Splashscreen.this.startActivity(myIntent);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

    }

}
