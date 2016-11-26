package neha.example.com.campusmap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by np6023 on 10/25/16.
 */

public class CampusCanvas extends ImageView {

    int ix;
    int iy;
    int ixc, iyc;
    Canvas canvas;
    static int xImage;
    static int yImage;
    private int childId;
    double curLat;
    double curLon;
    static boolean isFind;
    private float touchCircleRadius = (float) DefaultValues.TouchCircleRadius;

    private int markerColor;
    private Paint paint = new Paint();
    HashMap<String,String> buildFinal= new HashMap<String,String>();

    private float downX = -1;
    private float downY = -1;
    static int ixt;
    static int iyt;
    private float pressure = 1;
    private boolean isCancelled = false;

    private float stopChild1CaptureTimeOut = -1;
    private long beginChild1CaptureTime = -1;
    private long remainderChild1CaptureTime = 0;

    private boolean drawBorder = false;

    private boolean callBaseClass = true;
    private boolean handleOnTouchEvent = true;
    private boolean returnValueOnActionDown = true;
    private boolean returnValueOnActionMove = true;
    private boolean returnValueOnActionUp = true;
    private boolean returnValueOnLongClick = false;

    ArrayList<String> buildList = new ArrayList<String>();

    BuildingDetails buildingDetails = new BuildingDetails();

    private TouchListener touchListener;

    public CampusCanvas(Context context) {
        super(context);
        //ix=60;
        //iy=260;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        //canvas.drawText("kal", 0, 100, paint);


        // ND ++



        // ND 123



        /*
        Bitmap marker2 = BitmapFactory.decodeResource(getResources(),
                R.drawable.mapmarker);
        canvas.drawBitmap(marker2,390 ,220, null);

        Bitmap marker3 = BitmapFactory.decodeResource(getResources(),
                R.drawable.mapmarker);
        canvas.drawBitmap(marker3,390 ,520, null);

        Bitmap marker4 = BitmapFactory.decodeResource(getResources(),
                R.drawable.mapmarker);
        canvas.drawBitmap(marker4,30 ,520, null);

        */

        if(ix>0 && iy>0){
            Bitmap marker = BitmapFactory.decodeResource(getResources(),
                    R.drawable.mapmarker);
            canvas.drawBitmap(marker, ix, iy, null);
        }

        if(ixc>0 && iyc>0) {
            Paint mPaint = new Paint();
            mPaint.setColor(Color.RED);
            canvas.drawCircle(ixc, iyc, 10, mPaint);
        }

        canvas.save();
        super.onDraw(canvas);
        this.canvas=canvas;

        // ND --
    }

    public void setLocation (int x, int y) {
        ix = x;
        iy = y;
        invalidate();
    }

    public void setLoc(double longitude, double latitude) {
        this.curLat = latitude;
        this.curLon = longitude;
    }

    public void setLocCircle(int x, int y) {
        this.ixc = x;
        this.iyc = y;
        invalidate();
    }


    /*
    public Canvas reDraw(int x, int y){
        ix = x;
        iy = y;
        Bitmap marker = BitmapFactory.decodeResource(getResources(),
                R.drawable.mapmarker);
        canvas.drawBitmap(marker, ix, iy, null);
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        canvas.drawCircle(10, 10, 5, mPaint);
        canvas.save();
        return canvas;
    }
    */

    // ND Touch ++

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(callBaseClass)
        {
            super.onTouchEvent(event);
        }

        if(!handleOnTouchEvent)
        {
            return false;
        }

        int action = event.getAction();

        if(this.touchListener != null)
        {
            this.touchListener.onTouchHappened(childId, action, event.getX(), event.getY());
        }

        remainderChild1CaptureTime = Math.abs(beginChild1CaptureTime - System.currentTimeMillis());
        if((stopChild1CaptureTimeOut != -1)
                && (remainderChild1CaptureTime > stopChild1CaptureTimeOut)) {
            stopChild1CaptureTimeOut = -1;
            return false;
        }

        boolean result = false;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isCancelled = false;
                downX = event.getX();
                downY = event.getY();
                if (returnValueOnActionDown)
                {
                    result = returnValueOnActionDown;
                }
                //Toast.makeText(getContext(), "TOUCH downX: "+ downX + " downY: "+downY, Toast.LENGTH_SHORT).show();

                    ixt = (int)downX;
                    iyt = (int)downY;



                //Toast.makeText(getContext(), "IsFind CCND1 "+CampusCanvas.isFind, Toast.LENGTH_LONG).show();
                if(CampusCanvas.isFind) {
                    for (HashMap<String, String> build : buildingDetails.myBuildList) {
                        if (Integer.parseInt(build.get("ix")) + 40 > ixt && Integer.parseInt(build.get("ix")) - 40 < ixt && Integer.parseInt(build.get("iy")) + 40 > iyt && Integer.parseInt(build.get("iy")) - 40 < iyt && xImage == Integer.parseInt(build.get("ix")) && yImage == Integer.parseInt(build.get("iy"))) {

                            //buildFinal=build;
                            buildList = new ArrayList<String>(build.values());
                            //Toast.makeText(getContext(), "IsFind CCND2 "+CampusCanvas.isFind, Toast.LENGTH_LONG).show();
                            break;
                        }

                    }


                    if (buildList != null) {

                        Intent intent = new Intent(getContext(), BuildingDetailActivity.class);
                        intent.putExtra("lat", curLat);
                        intent.putExtra("lon", curLon);
                        intent.putExtra("buildList", buildList);


                        buildList = null;
                        CampusCanvas tcanvas = new CampusCanvas(getContext());
                        tcanvas.setLocation(0,0);

                        //Toast.makeText(getContext(), "IsFind CCND3 "+CampusCanvas.isFind, Toast.LENGTH_LONG).show();
                        CampusCanvas.isFind=false;
                        getContext().startActivity(intent);

                    }
                }



                break;
            case MotionEvent.ACTION_MOVE:
                isCancelled = false;
                downX = event.getX();
                downY = event.getY();
                if (returnValueOnActionMove)
                {
                    result = returnValueOnActionMove;
                }
                break;
            case MotionEvent.ACTION_UP:
                isCancelled = false;
                downX = -1;
                downY = -1;
                if (returnValueOnActionUp)
                {
                    result = returnValueOnActionUp;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                isCancelled = true;
                downX = event.getX();
                downY = event.getY();
                result = false;
                break;
            case MotionEvent.ACTION_OUTSIDE:
                isCancelled = false;
                break;
        }
        invalidate();

        return result;
    }



    // ND Touch --


}