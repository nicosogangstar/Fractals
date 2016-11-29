package com.nico.gonzo.fractals;

import android.view.GestureDetector;
import android.view.MotionEvent;

class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        MyGLRenderer.pan(distanceX, distanceY);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }
}
