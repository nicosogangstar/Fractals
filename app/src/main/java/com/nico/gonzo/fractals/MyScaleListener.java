package com.nico.gonzo.fractals;

import android.view.ScaleGestureDetector;

class MyScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        MyGLRenderer.mMandelbrot.zoom(detector.getScaleFactor());
        return true;
    }
}