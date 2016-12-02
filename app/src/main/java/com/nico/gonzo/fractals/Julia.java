package com.nico.gonzo.fractals;

import android.opengl.GLES20;

class Julia extends Fractal {

    private float[] juliaPos = {-0.7f, 0.27015f};
    private int mJuliaPosHandle;

    Julia(String shader, float iterations) {
        super(shader, iterations,
            new float[]{
                -1, 0,
                -1, -1,
                1, -1,

                -1, 0,
                1, 0,
                1, -1
            }
        );
    }

    @Override
    public void draw() {
        super.draw();
        mJuliaPosHandle = GLES20.glGetUniformLocation(super.mProgram, "juliaPos");
        GLES20.glUniform2fv(mJuliaPosHandle, 1, juliaPos, 0);
    }

    void setJuliaPos(float[] newPos) {
        juliaPos = newPos;
    }
}
