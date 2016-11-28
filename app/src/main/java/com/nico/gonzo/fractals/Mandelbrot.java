package com.nico.gonzo.fractals;

import android.opengl.GLES20;
import android.view.Display;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;

public class Mandelbrot {

    private FloatBuffer vertexBuffer, colorBuffer;

    private final int mProgram;

    private final String vertexShaderCode = MyGLRenderer.readShader("mandelbrot.vs.glsl");
    private final String fragmentShaderCode = MyGLRenderer.readShader("mandelbrot.fs.glsl");

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 2;
    static float[] coords = {
        -1,  1,
        -1, -1,
         1, -1,

        -1,  1,
         1,  1,
         1, -1
    };

    static float[] colors = {
        1.0f, 0.0f, 0.0f, 1.0f,
        0.0f, 1.0f, 0.0f, 1.0f,
        0.0f, 0.0f, 1.0f, 1.0f,

        1.0f, 0.0f, 0.0f, 1.0f,
        0.0f, 1.0f, 0.0f, 1.0f,
        0.0f, 0.0f, 1.0f, 1.0f
    };

    public Mandelbrot() {
        // Load the shaders
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

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

        // Allocate memory for the colors
        ByteBuffer cBuffer = ByteBuffer.allocateDirect(colors.length * 4);
        cBuffer.order(ByteOrder.nativeOrder());
        colorBuffer = cBuffer.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }

    // Uniforms
    private int uViewportDimensions, uBounds;

    // Finals
    private final float DIST = 2.0f;
    private final float[] bounds = new float[]{-DIST, DIST, -DIST, DIST},
            viewportDimensions = new float[]{360, 598};

    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexStride = 2 * 4,
                    colorStride = 4 * 4;
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    public void draw() {
        // Use the program
        GLES20.glUseProgram(mProgram);

        // Locate and activate the position attrib
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPos");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 2, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        // Locate and activate the color attrib
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "vColor");
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false, colorStride, colorBuffer);

        //GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, coords.length / 2);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}