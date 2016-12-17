package com.nico.gonzo.fractals;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

class MyGLSurfaceView extends GLSurfaceView {

    private MyGLRenderer mRenderer;
    private GestureDetectorCompat mGestureDetector = null;
    private ScaleGestureDetector mScaleDetector = null;

    public MyGLSurfaceView(Context context, int fractalType){
        super(context);
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer(context, fractalType);
        mGestureDetector = new GestureDetectorCompat(context, new MyGestureListener());
        mScaleDetector = new ScaleGestureDetector(context, new MyScaleListener());
        setRenderer(mRenderer);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        mScaleDetector.onTouchEvent(e);
        return true;
    }

}