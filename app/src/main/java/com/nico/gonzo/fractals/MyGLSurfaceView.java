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
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        Log.i("EVENT", e.toString());
        return super.onTouchEvent(e);
    }

}

class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
    private final String TAG = "MyGestureListener";
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        Log.i(TAG, "onScroll: deltaX=" + String.valueOf(e2.getX() - e1.getX()) + ", deltaY=" + String.valueOf(e2.getY() - e1.getY()));
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        Log.i(TAG, "onSingleTapUp: X=" + String.valueOf(e.getX()) + ", Y=" + String.valueOf(e.getY()));
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {
        Log.i(TAG, "onLongPress: X=" + String.valueOf(e.getX()) + ", Y=" + String.valueOf(e.getY()));
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }
}