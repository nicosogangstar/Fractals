package com.nico.gonzo.fractals;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    private GestureDetectorCompat mGestureDetector = null;

    public MyGLSurfaceView(Context context){
        super(context);

        mGestureDetector = new GestureDetectorCompat(context, new MyGestureListener());

        setEGLContextClientVersion(2);
        mRenderer = new MyGLRenderer(context.getAssets());
        setRenderer(mRenderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return super.onTouchEvent(e);
    }

}

class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
    private final String TAG = "MyGestureListener";
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        Log.e(TAG, "OnScroll: deltaX=" + String.valueOf(e2.getX() - e1.getX()) + ", deltaY=" + String.valueOf(e2.getY() - e1.getY()));
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        Log.e(TAG, "onSingleTapUp: X=" + String.valueOf(e.getX()) + ", Y=" + String.valueOf(e.getY()));
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {
        Log.e(TAG, "onLongPress: X=" + String.valueOf(e.getX()) + ", Y=" + String.valueOf(e.getY()));
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }
}