package com.nico.gonzo.fractals;

import android.opengl.GLES20;

class Mandelbrot extends Fractal {
    float[] bounds = {-2f, 2f, -2f, 2f};
    float n = 2.0f;
    private int mBoundsHandle, mNHandle;

    Mandelbrot(String shader) {
        super(shader);
    }

    @Override
    public void draw() {
        mBoundsHandle = GLES20.glGetUniformLocation(super.mProgram, "bounds");
        GLES20.glUniform4fv(mBoundsHandle, 1, bounds, 0);

        mNHandle = GLES20.glGetUniformLocation(mProgram, "n");
        GLES20.glUniform1f(mNHandle, n);
        super.draw();
    }
}
