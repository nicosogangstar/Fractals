package com.nico.gonzo.fractals;
import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class JuliaRenderer extends FractalRenderer implements GLSurfaceView.Renderer  {

    // Define fractal
    private static Julia mJulia;

    JuliaRenderer(Context context) {
        super(context);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mJulia = new Julia("julia", 350);
    }

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mJulia.setJuliaPos(Mandelbrot.getPosition());
        mJulia.draw();
    }

    public void onSurfaceChanged(GL10 unused, int _width, int _height) {
        Log.i("MyGLRenderer", _width + ":" + _height);
        GLES20.glViewport(0, 0, _width, _height);
        mJulia.setViewport(new float[]{_width, _height/2});
    }
}