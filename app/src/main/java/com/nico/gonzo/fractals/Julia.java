package com.nico.gonzo.fractals;

import android.opengl.GLES20;

class Julia extends Fractal {

    private float[] juliaPos = {.7f, -.23f};
    private int mJuliaPosHandle;

    Julia(String shader, float iterations) {
        super(shader, iterations);
    }

    @Override
    public void draw() {
        super.draw();
        mJuliaPosHandle = GLES20.glGetUniformLocation(super.mProgram, "juliaPos");
        GLES20.glUniform2fv(mJuliaPosHandle, 1, juliaPos, 0);
    }

    public void setJuliaPos(float[] newPos) {
        juliaPos = newPos;
    }

    public float[] getJuliaPos() {
        return juliaPos;
    }
}
