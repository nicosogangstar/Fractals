package com.nico.gonzo.fractals;
import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class MandelbrotRenderer extends FractalRenderer implements GLSurfaceView.Renderer {

    // Define fractals
    static Mandelbrot mMandelbrot;

    MandelbrotRenderer(Context context) {
        super(context);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mMandelbrot = new Mandelbrot("mandelbrot", 100);
    }

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mMandelbrot.draw();
    }

    public void onSurfaceChanged(GL10 unused, int _width, int _height) {
        Log.i("MyGLRenderer", _width + ":" + _height);
        GLES20.glViewport(0, 0, _width, _height);

        // Update fractals
        mMandelbrot.onResized();
        mMandelbrot.setViewport(new float[]{_width, _height/2});
    }
}