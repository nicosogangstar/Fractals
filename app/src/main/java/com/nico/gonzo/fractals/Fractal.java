package com.nico.gonzo.fractals;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.R.attr.color;
import static android.R.attr.height;
import static android.R.attr.width;

class Fractal {
    private final String TAG = "Fractal";
    final int mProgram;
    float[] bounds = {-2f, 2f, -2f, 2f};
    private FloatBuffer vertexBuffer;
    private float[] viewport;
    private int coloringType;

    // number of coordinates per vertex in this array
    private float[] coords;

    // Attrib handles
    private int mPositionHandle,

    // Uniform handles
    mViewportHandle, mMaxIterationsHandle, mColoringTypeHandle,

    vertexStride = 2 * 4;

    Fractal(String shader, float mIts, int colorT, float[] newCoords) {
        coords = newCoords;
        coloringType = colorT;

        // Load the shaders
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, MyGLRenderer.readShader("fractal.vs.glsl"));
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, MyGLRenderer.readShader(shader + ".fs.glsl"));

        // Create the program
        mProgram = GLES20.glCreateProgram();

        // Attach the shaders to the program and link the program
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);

        // Clear the screen
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        // Allocate memory for the coordinates
        ByteBuffer vBuffer = ByteBuffer.allocateDirect(coords.length * 4);
        vBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = vBuffer.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        // Use the program
        GLES20.glUseProgram(mProgram);

        mMaxIterationsHandle = GLES20.glGetUniformLocation(mProgram, "maxIterations");
        GLES20.glUniform1f(mMaxIterationsHandle, mIts);
    }

    void draw() {
        // Locate and activate the position attrib
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPos");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 2, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        mViewportHandle = GLES20.glGetUniformLocation(mProgram, "viewportDimensions");
        GLES20.glUniform2fv(mViewportHandle, 1, viewport, 0);

        mColoringTypeHandle = GLES20.glGetUniformLocation(mProgram, "coloringType");
        GLES20.glUniform1i(mColoringTypeHandle, coloringType);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, coords.length / 2);

        // Disable attribs
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    void setViewport(float[] input) {
        viewport = input;
    }

    float[] getPosition() {
        return new float[]{(bounds[3] + bounds[2]) / 2, (bounds[0] + bounds[1]) / 2};
    }

    void onResized() {
        // Real number axis adjustment
        float rangeR = bounds[3] - bounds[2];
        bounds[3] = (float)((bounds[1] - bounds[0]) * (width / height) / 1.4 + bounds[2]);
        float newRangeR = bounds[3] - bounds[2];
        bounds[2] -= (newRangeR - rangeR) / 2;
        bounds[3] = (float)((bounds[1] - bounds[0]) * (width / height) / 1.4 + bounds[2]);
    }

    void zoom(float rangeModifier) {
        // Imaginary number axis adjustment
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