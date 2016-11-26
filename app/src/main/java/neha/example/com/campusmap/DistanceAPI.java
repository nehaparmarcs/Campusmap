package neha.example.com.campusmap;

import android.util.Log;
import android.widget.Toast;

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.jackson.JacksonFactory;

/**
 * Created by np6023 on 10/29/16.
 */
public class DistanceAPI {

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    // Google API Key
    private static final String API_KEY = "AIzaSyBtISpVr3SJC7lnTkNySQ4UL7FTMQRvCF4";

    // Google Places serach url's
    private static final String DISTANCE_SEARCH_URL1 = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
    private static final String DISTANCE_SEARCH_URL2 = ",";
    private static final String DISTANCE_SEARCH_URL3 = "&destinations=";
    private static final String DISTANCE_SEARCH_URL4 =  "&mode=walking&sensor=false";
    private double latitudeS;
    private double longitudeS;
    private double latitudeD;
    private double longitudeD;

    public Position search(double latitudeS, double longitudeS, double latitudeD, double longitudeD)
            throws Exception {

        this.latitudeS = latitudeS;
        this.longitudeS = longitudeS;
        this.latitudeD = latitudeD;
        this.longitudeD = longitudeD;
        String url = DISTANCE_SEARCH_URL1+this.latitudeS+DISTANCE_SEARCH_URL2+this.longitudeS+DISTANCE_SEARCH_URL3+this.latitudeD+DISTANCE_SEARCH_URL2+this.longitudeD+DISTANCE_SEARCH_URL4;

        //Toast.makeText(getBaseContext(), url, Toast.LENGTH_LONG).show();
        System.out.println(url);
        //Log.d("Places Status NDND", "" + url);
        try {

            Log.d("Places Status NDND1", "" + url);
            HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
            //Log.d("Places Status NDND2", "" + url);
            HttpRequest request = httpRequestFactory
                    .buildGetRequest(new GenericUrl(url));


            //Log.d("Places Status NDND3", "" + url);
            //System.out.println("Starting Distance Cal ");
            Log.d("Starting Distance Cal ", "" );
            //Toast.makeText(getBaseContext(), "Starting Distance Cal "+position.duration, Toast.LENGTH_LONG).show();
            Position list = request.execute().parseAs(Position.class);
            //System.out.println("Ending Distance Cal distance: "+ list.distance+" duration: "+list.duration);
            Log.d("Ending Distance Cal dis", "" );
            //Toast.makeText(getBaseContext(), "Starting Distance Cal "+position.duration, Toast.LENGTH_LONG).show();
            // Check log cat for places response status
            return list;

        } catch (HttpResponseException e) {
            Log.e("Error:", e.getMessage());
            return null;
        }

    }

    public static HttpRequestFactory createRequestFactory(
            final HttpTransport transport) {
        return transport.createRequestFactory(new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
                GoogleHeaders headers = new GoogleHeaders();
                headers.setApplicationName("AndroidHive-Places-Test");
                request.setHeaders(headers);
                JsonHttpParser parser = new JsonHttpParser(new JacksonFactory());
                request.addParser(parser);
            }
        });
    }



}
