package neha.example.com.campusmap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

/**
 * Created by np6023 on 10/28/16.
 */


public class MapFragment extends SupportMapFragment implements
        GoogleMap.OnIndoorStateChangeListener,
        GoogleMap.OnMapLongClickListener,
        SeekBar.OnSeekBarChangeListener, OnMapReadyCallback {

    private IndoorBuilding mIndoorBuilding;
    private SeekBar mIndoorSelector;
    private TextView mIndoorMinLevel;
    private TextView mIndoorMaxLevel;

    private StreetViewPanoramaView mStreetViewPanoramaView;
    private StreetViewPanorama mPanorama;
    SupportMapFragment mapFragment;
    GoogleMap googleMap;

    //static double dLat=37.3368158;
    //static double dlon=-121.8812539;
    static double dLat=37.3368158;
    static double dlon=-121.8812539;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup parent = (ViewGroup) super.onCreateView( inflater, container, savedInstanceState );
        View overlay = inflater.inflate( R.layout.view_map_overlay, parent, false );


        //String strLat = this.getArguments().getString("latitude");
        //String strLon = this.getArguments().getString("longitude");

        /*
        MapsActivity activity = (MapsActivity) getActivity();
        String strLat = activity.getLat();
        String strLon = activity.getLon();
        */



        mIndoorSelector = (SeekBar) overlay.findViewById( R.id.indoor_level_selector );
        mIndoorMinLevel = (TextView) overlay.findViewById( R.id.indoor_min_level );
        mIndoorMaxLevel = (TextView) overlay.findViewById( R.id.indoor_max_level );


        mStreetViewPanoramaView = (StreetViewPanoramaView) overlay.findViewById(R.id.steet_view_panorama);
        mStreetViewPanoramaView.onCreate(savedInstanceState);

        parent.addView(overlay);

        return parent;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null)
        {
            String strLat = this.getArguments().getString("latitude");
            String strLon = this.getArguments().getString("longitude");

            /* ND temp ++
            dLat=Double.parseDouble(strLat);
            dlon=Double.parseDouble(strLon);

            ND temp -- */



            //listMusics = bundle.getParcelableArrayList("arrayMusic");
            //listMusic.setAdapter(new MusicBaseAdapter(getActivity(), listMusics));
        }
    }

    private void showStreetView( LatLng latLng ) {
        if( mPanorama == null )
            return;

        StreetViewPanoramaCamera.Builder builder = new StreetViewPanoramaCamera.Builder( mPanorama.getPanoramaCamera() );
        builder.tilt( 0.0f );
        builder.zoom( 0.0f );
        builder.bearing( 0.0f );
        mPanorama.animateTo( builder.build(), 0 );

        mPanorama.setPosition( latLng, 300 );
        mPanorama.setStreetNamesEnabled( true );
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCamera();
        hideFloorLevelSelector();
        initMapIndoorSelector();
        initStreetView();
    }

    private void initStreetView() {


        //getMap().setOnMapLongClickListener( this );

        if(googleMap!=null) {
            googleMap.setOnMapLongClickListener( this );
        }

        mStreetViewPanoramaView.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                mPanorama = panorama;
                showStreetView( new LatLng(dLat, dlon ) );
            }
        });
    }

    private void initCamera() {
        CameraPosition position = CameraPosition.builder()
                .target( new LatLng(dLat, dlon) )
                .zoom( 18f )
                .bearing( 0.0f )
                .tilt( 0.0f )
                .build();


        //getMap().animateCamera( CameraUpdateFactory.newCameraPosition( position ), null );
        //getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if(googleMap!=null) {
            googleMap.animateCamera( CameraUpdateFactory.newCameraPosition( position ), null );
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    }

    private void initMapIndoorSelector() {
        mIndoorSelector.setOnSeekBarChangeListener( this );

        //getMap().getUiSettings().setIndoorLevelPickerEnabled( false );
        //getMap().setOnIndoorStateChangeListener( this );

        if(googleMap!=null) {
            googleMap.getUiSettings().setIndoorLevelPickerEnabled( false );
            googleMap.setOnIndoorStateChangeListener( this );
        }
    }

    private void hideFloorLevelSelector() {
        mIndoorSelector.setVisibility( View.GONE );
        mIndoorMaxLevel.setVisibility( View.GONE );
        mIndoorMinLevel.setVisibility( View.GONE );
    }

    private void showFloorLevelSelector() {
        if( mIndoorBuilding == null )
            return;

        int numOfLevels = mIndoorBuilding.getLevels().size();

        mIndoorSelector.setMax( numOfLevels - 1 );

        //Bottom floor is the last item in the list, top floor is the first
        mIndoorMaxLevel.setText( mIndoorBuilding.getLevels().get( 0 ).getShortName() );
        mIndoorMinLevel.setText( mIndoorBuilding.getLevels().get( numOfLevels - 1 ).getShortName() );

        mIndoorSelector.setProgress( mIndoorBuilding.getActiveLevelIndex() );

        mIndoorSelector.setVisibility( View.VISIBLE );
        mIndoorMaxLevel.setVisibility( View.VISIBLE );
        mIndoorMinLevel.setVisibility( View.VISIBLE );

    }

    @Override
    public void onIndoorBuildingFocused() {
        //mIndoorBuilding = getMap().getFocusedBuilding();

        if(googleMap!=null) {
            mIndoorBuilding = googleMap.getFocusedBuilding();
        }

        if( mIndoorBuilding == null || mIndoorBuilding.getLevels() == null || mIndoorBuilding.getLevels().size() <= 1 ) {
            hideFloorLevelSelector();
        } else {
            showFloorLevelSelector();
        }

    }

    @Override
    public void onIndoorLevelActivated(IndoorBuilding indoorBuilding) {
        if( indoorBuilding == null )
            return;


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if( mIndoorBuilding == null || seekBar.getVisibility() == View.GONE  )
            return;

        int numOfLevels = mIndoorBuilding.getLevels().size();
        mIndoorBuilding.getLevels().get( numOfLevels - 1 - progress ).activate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        showStreetView( latLng );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
