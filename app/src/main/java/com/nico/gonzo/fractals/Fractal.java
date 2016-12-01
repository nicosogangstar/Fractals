package com.nico.gonzo.fractals;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Fractal {
    private final String TAG = "Fractal";
    protected final int mProgram;
    protected FloatBuffer vertexBuffer;
    protected float[] viewport;
    protected float iterations;

    // number of coordinates per vertex in this array
    private static float[] coords = {
        -1,  1,
        -1, -1,
         1, -1,

        -1,  1,
         1,  1,
         1, -1
    };

    Fractal(String shader, float iterations) {
        // Load the shaders
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, MyGLRenderer.readShader("fractal.vs.glsl"));
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, MyGLRenderer.readShader(shader + ".fs.glsl"));

        // Create the program
        mProgram = GLES20.glCreateProgram();

        // Attach the shaders to the program and link the program
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);

        // Allocate memory for the coordinates
        ByteBuffer vBuffer = ByteBuffer.allocateDirect(coords.length * 4);
        vBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = vBuffer.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        // Set the max iterations
        this.iterations = iterations;
    }

    // Attrib handles
    int mPositionHandle;

    // Uniform handles
    int mViewportHandle, mMaxIterationsHandle;

    final int vertexStride = 2 * 4;

    void draw() {
        // Clear the screen
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        // Use the program
        GLES20.glUseProgram(mProgram);

        // Locate and activate the position attrib
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPos");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 2, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        mViewportHandle = GLES20.glGetUniformLocation(mProgram, "viewportDimensions");
        GLES20.glUniform2fv(mViewportHandle, 1, viewport, 0);

        mMaxIterationsHandle = GLES20.glGetUniformLocation(mProgram, "maxIterations");
        GLES20.glUniform1f(mMaxIterationsHandle, iterations);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, coords.length / 2);

        // Disable attribs
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    void setViewport(float[] input) {
        viewport = input;
    }
}