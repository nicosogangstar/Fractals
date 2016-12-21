package com.nico.gonzo.fractals;

import android.opengl.GLES20;

class Mandelbrot extends Fractal {

    private int mBoundsHandle;

    Mandelbrot(float iterations, int colorType, float[] coords) {
        super("mandelbrot", iterations, colorType, coords);
    }

    @Override
    public void draw() {
        super.draw();

        mBoundsHandle = GLES20.glGetUniformLocation(super.mProgram, "bounds");
        GLES20.glUniform4fv(mBoundsHandle, 1, bounds, 0);
    }
}
