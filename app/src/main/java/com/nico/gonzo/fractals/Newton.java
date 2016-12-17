package com.nico.gonzo.fractals;

import android.opengl.GLES20;

class Newton extends Fractal {

    private final String TAG = "Newton";

    private float n = 2.0f;
    private int mBoundsHandle;

    Newton(float iterations, float[] coords) {
        super("newton", iterations, coords);
    }

    @Override
    public void draw() {
        super.draw();

        mBoundsHandle = GLES20.glGetUniformLocation(super.mProgram, "bounds");
        GLES20.glUniform4fv(mBoundsHandle, 1, bounds, 0);
    }
}
