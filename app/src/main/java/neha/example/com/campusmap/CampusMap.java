package neha.example.com.campusmap;

import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by np6023 on 10/25/16.
 */
public class CampusMap extends AppCompatActivity {

    FrameLayout frameLayout;
    ImageView imageView;
    Canvas canvas;
    int ix, iy;

    Handler mHandler;
    Button btnSrch;

    Location locationFinal;
    CampusCanvas tcanvas;
    HashMap<String, String> buildFinal = new HashMap<String, String>();
    boolean isFind;
    int ixt, iyt;
    ArrayList<String> buildList = new ArrayList<String>();
    ;
    Context context;
    double longitude;
    double latitude;
    AutoCompleteTextView txtSrch;
    String[] buildings = {"KING LIBRARY", "ENGINEERING BUILDING", "YOSHIHIRO UCHIDA HALL", "STUDENT UNION", "BOCCARDO BUSINESS COMPLEX", "SOUTH PARKING GARAGE"};

    LocationManager locationManager;
    LocationManager lm;
    String srch;
    //Location location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_campus);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        context = getBaseContext();
        tcanvas = new CampusCanvas(this);

        /*
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 200, 1, locationListenerGPS);
        */


        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200,
                10, mLocationListener);
        tcanvas.setLoc(longitude, latitude);


        Log.d("Location Manager Obj ", " "+lm);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */


                // ND ++ location

                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();


                    //Toast.makeText(getBaseContext(), "Known Latitude: " + latitude + " longitude: " + longitude, Toast.LENGTH_LONG).show();
                }

                // ND ++

                //locationManager.removeUpdates(locationListenerGPS);


                // ND --

            }
        });


        int lxi = 70, hxi = 390, lyi = 260, hyi = 530, dxi = 360, dyi = 300;
        double lxm = 37.33388898, hxm = 37.338525, lym = 121.88572465, hym = 121.885908, dxm = 0.004027, dym = 0.009399, qx = 0, qy = 0;
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location!=null){
             longitude = location.getLongitude();
             latitude = location.getLatitude();
            //Toast.makeText(getBaseContext(), "Latitude: "+latitude+" longitude: "+ longitude , Toast.LENGTH_LONG).show();
            //tcanvas.setLoc(longitude, latitude);
        }

        if(longitude != 0.0 && latitude != 0.0){
            tcanvas.setLoc(longitude, latitude);
        }
        else{
            //Toast.makeText(getBaseContext(), "Not Assigning Latitude "+latitude+" and longitude: "+ longitude , Toast.LENGTH_LONG).show();
        }
        // ND -- location


        longitude =-longitude;

        //Toast.makeText(getBaseContext(), "Lati " + latitude + ", longi " + longitude, Toast.LENGTH_LONG).show();
        qx = latitude - lxm;
        qy = longitude - lym;
        //Toast.makeText(getBaseContext(), "qx " + qx + ", qy " + qy, Toast.LENGTH_LONG).show();
        double fx = qx/dxm;
        double fy = qy/dym;
        //Toast.makeText(getBaseContext(), "fx " + fx + ", fy " + fx, Toast.LENGTH_LONG).show();
        double xx = dxi * fx;
        double yy = dyi * fy;
        //Toast.makeText(getBaseContext(), "xx " + xx + ", yy " + yy, Toast.LENGTH_LONG).show();

        ix = (int)xx + lxi;

        iy = (int)yy + lyi;

        //Toast.makeText(getBaseContext(), "ix " + ix + ", ix " + iy, Toast.LENGTH_LONG).show();

        imageView = (ImageView) this.findViewById(R.id.imageView1);



        frameLayout = (FrameLayout) findViewById(R.id.frameCam);
        frameLayout.addView(tcanvas);

        tcanvas.setLocCircle(ix, iy);
        frameLayout.removeView(tcanvas);
        frameLayout.addView(tcanvas);

        txtSrch=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,buildings);

        txtSrch.setAdapter(adapter);
        txtSrch.setThreshold(1);



        btnSrch = (Button) findViewById(R.id.btnSearch);
        //Toast.makeText(getBaseContext(), "IsFind ND4 "+isFind, Toast.LENGTH_LONG).show();


        if (btnSrch != null ) {
            btnSrch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(txtSrch.getText().toString()!=null && !(txtSrch.getText().toString().equals(""))){

                    srch = txtSrch.getText().toString();
                    BuildingDetails buildingDetails = new BuildingDetails();
                    //Toast.makeText(getBaseContext(), "IsFind ND5 " + isFind, Toast.LENGTH_LONG).show();

                    for (HashMap<String, String> build : buildingDetails.myBuildList) {
                        //Toast.makeText(getBaseContext(), "Size of ArrayList "+ buildingDetails.myBuildList.size(), Toast.LENGTH_LONG).show();
                        if (build.get("sname").equalsIgnoreCase(srch) || build.get("name").equalsIgnoreCase(srch)) {
                            buildFinal = build;
                            //Toast.makeText(getBaseContext(), "Found the hashMap ", Toast.LENGTH_LONG).show();
                            tcanvas.setLocation(Integer.parseInt(buildFinal.get("ix")), (Integer.parseInt(buildFinal.get("iy"))));
                            //Toast.makeText(getBaseContext(), "Displayed on the Map", Toast.LENGTH_LONG).show();
                            buildList = new ArrayList<String>(buildFinal.values());
                            CampusCanvas.xImage=Integer.parseInt(buildFinal.get("ix"));
                            CampusCanvas.yImage=Integer.parseInt(buildFinal.get("iy"));
                            CampusCanvas.isFind=true;
                            txtSrch.setText("");
                            isFind = false;
                            //Toast.makeText(getBaseContext(), "IsFind ND6 " + isFind, Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }

                }
            });
        }

        /*
        Button btnMy = (Button) findViewById(R.id.btnMy);
        if (btnMy != null) {
            btnMy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    // ND ++ location

                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();


                        Toast.makeText(getBaseContext(), "Current Latitude: " + latitude + " longitude: " + longitude, Toast.LENGTH_LONG).show();
                    }



                }
            });
        }
        */

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            //Toast.makeText(CampusMap.this,"in runnable",Toast.LENGTH_SHORT).show();

            CampusMap.this.mHandler.postDelayed(m_Runnable,2000);
        }

    };

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            //Toast.makeText(CampusMap.this,"Inside Listener"+ latitude +", "+longitude,Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    // ND ++ Location


        // ND -- Location
    }

