package com.nico.gonzo.fractals;

import android.opengl.GLES20;
import android.view.Display;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;

public class Mandelbrot {

    private FloatBuffer vertexBuffer;

    private final int mProgram;

    private final String vertexShaderCode = MyGLRenderer.readShader("mandelbrot.vs.glsl");

    private final String fragmentShaderCode = MyGLRenderer.readShader("mandelbrot.fs.glsl");

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 2;
    static float coords[] = {   // in counterclockwise order:
        -1,  1,
        -1, -1,
         1, -1,

        -1,  1,
         1,  1,
         1, -1
    };


    public Mandelbrot() {
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);


        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                coords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(coords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
    }


    // Uniforms
    private int uViewportDimensions, uBounds;

    // Finals
    private final float DIST = 2.0f;
    private final float[] bounds = new float[]{-DIST, DIST, -DIST, DIST},
            viewportDimensions = new float[]{360, 598};

    public void draw() {
        GLES20.glUseProgram(mProgram);

        uViewportDimensions = GLES20.glGetUniformLocation(mProgram, "viewportDimensions");
        uBounds = GLES20.glGetUniformLocation(mProgram, "bounds");

        int vPosAttrib = GLES20.glGetAttribLocation(mProgram, "vPos");

        GLES20.glEnableVertexAttribArray(vPosAttrib);

        GLES20.glVertexAttribPointer(vPosAttrib, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 8, 0);

        GLES20.glUniform4fv(uBounds, 1, bounds, 0);
        GLES20.glUniform2fv(uViewportDimensions, 1, viewportDimensions, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

        GLES20.glDisableVertexAttribArray(vPosAttrib);
    }
}