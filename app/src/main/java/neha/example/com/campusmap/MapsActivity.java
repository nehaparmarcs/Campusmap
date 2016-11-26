package neha.example.com.campusmap;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String strLat;
    String strLon;
    double dLat;
    double dLon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //this.getActionBar().setDisplayHomeAsUpEnabled(true);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        dLat = getIntent().getDoubleExtra("latitude",0);
        dLon = getIntent().getDoubleExtra("longitude",0);
        String name = getIntent().getStringExtra("name");
        MapFragment.dLat = dLat;
        MapFragment.dlon = dLon;
        setTitle(name);

        //Bundle bundle = new Bundle();
        //bundle.putString("latitude", strLat);
        //bundle.putString("longitude", strLon);
        // set Fragmentclass Arguments
        //MapFragment fragobj = new MapFragment();
        //fragobj.setArguments(bundle);
        //Toast.makeText(getBaseContext(), "Lat long " + strLat+strLon, Toast.LENGTH_LONG).show();
        //bundle.putString("latitude", "11.777777");
        //bundle.putString("longitude", "11.1111");


        //getFragmentManager().beginTransaction().add(R.id., f).commit();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        /* ND ++ Temp
        mapFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, mapFragment).commit();
        ND -- Temp */
        mapFragment.getMapAsync(this);


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


    public String getLat() {
        return strLat;
    }

    public String getLon() {
        return strLon;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    /* ND ++ Map top
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    ND -- Map top */
}
