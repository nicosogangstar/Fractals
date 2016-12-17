package com.nico.gonzo.fractals;

import android.opengl.GLES20;

class Julia extends Fractal {

    private int mJuliaPosHandle;

    Julia(float iterations, float[] coords) {
        super("julia", iterations, coords);
    }

    @Override
    public void draw() {
        super.draw();
        mJuliaPosHandle = GLES20.glGetUniformLocation(super.mProgram, "juliaPos");
        GLES20.glUniform2fv(mJuliaPosHandle, 1, getPosition(), 0);
    }

    @Override
    void zoom(float rangeModifier) {}
}
