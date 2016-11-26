package neha.example.com.campusmap;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by np6023 on 10/29/16.
 */
public class TouchVisualizerViewGroupView extends ViewGroup implements TouchListener{


    private float lastInterceptX = -1;
    private float lastInterceptY = -1;

    private float downX = -1;
    private float downY = -1;

    private int childId = -1;
    private int childAction = -1;
    private float childDownX = -1;
    private float childDownY = -1;

    public TouchVisualizerViewGroupView(Context context) {
        super(context);
    }

    @Override
    public void onTouchHappened(int child, int action, float x, float y) {
        childId = child;
        childAction = action;
        if(action != MotionEvent.ACTION_UP && action != MotionEvent.ACTION_CANCEL) {
            childDownX = x;
            childDownY = y;
        }
        else
        {
            childDownX = -1;
            childDownY = -1;
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
