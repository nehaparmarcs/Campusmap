package neha.example.com.campusmap;

import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by np6023 on 10/27/16.
 */
public class BuildingDetailActivity extends AppCompatActivity {


    TextView txtSN, txtLN, txtAdd, txtLat, txtLong;
    Button  btnStreet;
    HashMap<String,String> buildFinal= new HashMap<String,String>();
     ArrayList<String> myListF = new ArrayList<String>();
    ImageView imageBuild;
    Drawable drawable;
    String uri;
    DistanceAPI distanceAPI = new DistanceAPI();;
    Position position = new Position();
    double curLat;
    double curLon;
    static String name, sname, addre, lat, lon;
    Bundle savedInstanceState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_building);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        myListF = getIntent().getStringArrayListExtra("buildList");
        curLat = getIntent().getDoubleExtra("lat",0);
        curLon = getIntent().getDoubleExtra("lon",0);
        new LoadPlaces().execute(String.valueOf(curLat),String.valueOf(curLon),myListF.get(6),myListF.get(3));
        this.savedInstanceState = savedInstanceState;




        curLat = getIntent().getDoubleExtra("lat",0);
        curLon = getIntent().getDoubleExtra("lon",0);

        Toast.makeText(getBaseContext(), "Lat value in Detail " + curLat, Toast.LENGTH_LONG).show();
        Toast.makeText(getBaseContext(), "Lon value in Detail " + curLon, Toast.LENGTH_LONG).show();



        imageBuild = (ImageView) findViewById(R.id.image123);

        //name=myListF.get(1);
        setTitle(myListF.get(1));

        String img = myListF.get(2);
        if(img.equals("BBC")){
            //imageBuild.setImageResource(R.drawable.bbc);
            //uri = "@drawable/bbc";
            //drawable  = getResources().getDrawable(R.drawable.bbc);
            imageBuild.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("drawable/bbc", "drawable", getPackageName())));
        }
        else if(img.equals("EB")){
            //imageBuild.setImageResource(R.drawable.eb);
            //uri = "@drawable/eb";
            //drawable  = getResources().getDrawable(R.drawable.eb);
            imageBuild.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("drawable/eb", "drawable", getPackageName())));
        }
        else if(img.equals("KL")){
            //imageBuild.setImageResource(R.drawable.kl);
            //uri = "@drawable/kl";
            //drawable  = getResources().getDrawable(R.drawable.kl);
            imageBuild.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("drawable/kl", "drawable", getPackageName())));
        }
        else if(img.equals("SPG")){
            //imageBuild.setImageResource(R.drawable.spg);
            //uri = "@drawable/spg";
            //drawable  = getResources().getDrawable(R.drawable.spg);
            imageBuild.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("drawable/spg", "drawable", getPackageName())));
        }
        else if(img.equals("SU")){
            //imageBuild.setImageResource(R.drawable.su);
            //uri = "@drawable/su";
            //drawable  = getResources().getDrawable(R.drawable.su);
            imageBuild.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("drawable/su", "drawable", getPackageName())));
        }
        else if(img.equals("YUH")){
            //imageBuild.setImageResource(R.drawable.yuh);
            //uri = "@drawable/yuh";
            //drawable  = getResources().getDrawable(R.drawable.yuh);
            imageBuild.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("drawable/yuh", "drawable", getPackageName())));
        }




            txtAdd = (TextView) findViewById(R.id.textViewAddD);
             txtAdd.setText(myListF.get(0));



             if (btnStreet != null) {
                btnStreet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        intent.putExtra("latitude", Double.parseDouble(myListF.get(6)));
                        intent.putExtra("longitude", Double.parseDouble(myListF.get(3)));
                        intent.putExtra("name", myListF.get(1));

                        startActivityForResult(intent,111);

                    }
                });
            }
             FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
             fab.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                     intent.putExtra("latitude", Double.parseDouble(myListF.get(6)));
                     intent.putExtra("longitude", Double.parseDouble(myListF.get(3)));
                     intent.putExtra("name", myListF.get(1));

                     //Toast.makeText(getBaseContext(), "Longggg " + myListF.get(6)+"Long  "+myListF.get(3), Toast.LENGTH_LONG).show();
                     startActivityForResult(intent,111);
                 }
             });

        //myListF = myList;


    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putStringArrayList("myList",myListF);
        // etc.
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        myListF = savedInstanceState.getStringArrayList("myList");

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



    /**
     * Background Async Task to Load Google places
     * */
    class LoadPlaces extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * getting Places JSON
         * */
        protected String doInBackground(String... args) {
            // creating Places class object
            //Toast.makeText(getBaseContext(), "Starting Distance Cal "+position.duration, Toast.LENGTH_LONG).show();

            double mylat = Double.parseDouble(args[0]);
            double mylng = Double.parseDouble(args[1]);
            double passedLat = Double.parseDouble(args[2]);
            double passedLng = Double.parseDouble(args[3]);


            try {

                position = distanceAPI.search(mylat,mylng, passedLat, passedLng);
                Log.d("My Lat ", ", "+mylat + " curlon "+ mylng );
                Log.d("Passed Lat ", ""+passedLat + ", Long "+ passedLng );


                Log.d("BDA Distance:   ", "" + position.rows[0].elements[0].distance.text+" Time: "+position.rows[0].elements[0].duration.text);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



        /**
         * After completing background task Dismiss the progress dialog
         * and show the data in UI
         * Always use runOnUiThread(new Runnable()) to update UI from background
         * thread, otherwise you will get error
         * **/

        protected void onPostExecute(String file_url) {

            txtLat = (TextView) findViewById(R.id.textViewLatD);
            txtLong = (TextView) findViewById(R.id.textViewLonD);
            //if(position.rows[0].elements[0].distance.text!=null)
            if(position!=null){
                if(position.rows!=null){
                    if(position.rows[0].elements!=null){
                        if(position.rows[0].elements[0].distance!=null){
                            txtLat.setText(""+position.rows[0].elements[0].distance.text);
                        }
                        if(position.rows[0].elements[0].duration!=null){
                            txtLong.setText(""+position.rows[0].elements[0].duration.text);
                        }
                    }
                }
            }



            //if(position.rows[0].elements[0].duration.text!=null)


        }


        }








}
