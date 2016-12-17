package com.nico.gonzo.fractals;

import android.view.GestureDetector;
import android.view.MotionEvent;

class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        MyGLRenderer.fractal.pan(distanceX, distanceY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //MyGLRenderer.fractal.setN(MyGLRenderer.mMandelbrot.getN() + .1f);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }
}
