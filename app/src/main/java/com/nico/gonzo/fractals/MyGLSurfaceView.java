package com.nico.gonzo.fractals;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.MotionEvent;

class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    private GestureDetectorCompat mGestureDetector = null;

    public MyGLSurfaceView(Context context){
        super(context);

        mGestureDetector = new GestureDetectorCompat(context, new MyGLRenderer.MyGestureListener());

        setEGLContextClientVersion(2);
        mRenderer = new MyGLRenderer(context.getAssets());
        setRenderer(mRenderer);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        mRenderer.zoom(0.95f);
        Log.i("EVENT", e.toString());
        return super.onTouchEvent(e);
    }

}