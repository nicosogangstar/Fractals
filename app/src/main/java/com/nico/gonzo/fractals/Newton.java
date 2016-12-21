package com.nico.gonzo.fractals;

import android.opengl.GLES20;

class Newton extends Fractal {

    private int mBoundsHandle;

    Newton(float iterations, int colorType, float[] coords) {
        super("newton", iterations, 2, coords);
    }

    @Override
    public void draw() {
        super.draw();

        mBoundsHandle = GLES20.glGetUniformLocation(super.mProgram, "bounds");
        GLES20.glUniform4fv(mBoundsHandle, 1, bounds, 0);
    }
}
