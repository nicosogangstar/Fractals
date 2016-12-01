package com.nico.gonzo.fractals;

import android.opengl.GLES20;

import static android.R.attr.height;
import static android.R.attr.width;

class Mandelbrot extends Fractal {

    private final String TAG = "Mandelbrot";
    private static float[] bounds = {-2f, 2f, -2f, 2f};
    private float n = 2.0f;
    private int mBoundsHandle, mNHandle;

    Mandelbrot(String shader, float iterations) {
        super(shader, iterations);
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

    static float[] getPosition() {
        return new float[]{(bounds[3] + bounds[2]) / 2, (bounds[0] + bounds[1]) / 2};
    }

    void onResized() {
        float rangeR = bounds[3] - bounds[2];
        bounds[3] = (float)((bounds[1] - bounds[0]) * (width / height) / 1.4 + bounds[2]);
        float newRangeR = bounds[3] - bounds[2];
        bounds[2] -= (newRangeR - rangeR) / 2;
        bounds[3] = (float)((bounds[1] - bounds[0]) * (width / height) / 1.4 + bounds[2]);
    }

    void zoom(float rangeModifier) {
        float rangeI = bounds[1] - bounds[0];
        float newRangeI;
        newRangeI = rangeI / rangeModifier;
        float delta = newRangeI - rangeI;
        bounds[0] -= delta / 2;
        bounds[1] = bounds[0] + newRangeI;
        onResized();
    }

    void pan(float distI, float distR) {
        float rangeI = bounds[1] - bounds[0];
        float rangeR = bounds[3] - bounds[2];

        float deltaI = (distR / viewport[1]) * rangeI;
        float deltaR = (distI / viewport[0]) * rangeR;

        bounds[0] -= deltaI;
        bounds[1] -= deltaI;
        bounds[2] += deltaR;
        bounds[3] += deltaR;
    }
}
