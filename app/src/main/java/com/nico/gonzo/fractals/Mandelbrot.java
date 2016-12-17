package com.nico.gonzo.fractals;

import android.opengl.GLES20;

import static android.R.attr.height;
import static android.R.attr.width;

class Mandelbrot extends Fractal {

    private final String TAG = "Mandelbrot";

    private float n = 2.0f;
    private int mBoundsHandle, mNHandle;

    Mandelbrot(float iterations, float[] coords) {
        super("mandelbrot", iterations, coords);
    }

    @Override
    public void draw() {
        super.draw();

        mBoundsHandle = GLES20.glGetUniformLocation(super.mProgram, "bounds");
        GLES20.glUniform4fv(mBoundsHandle, 1, bounds, 0);

        mNHandle = GLES20.glGetUniformLocation(mProgram, "n");
        GLES20.glUniform1f(mNHandle, n);
    }

    void setN(float n) {
        this.n = n;
    }

    float getN() {
        return n;
    }
}
