package com.nico.gonzo.fractals;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.R.attr.width;
import static android.R.attr.height;


class MyGLRenderer implements GLSurfaceView.Renderer {

    private Mandelbrot mMandelbrot;
    private int prevWidth, prevHeight;

    // TODO fix this misuse of static
    static private AssetManager assetManager;

    MyGLRenderer(AssetManager assets) {
        assetManager = assets;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mMandelbrot = new Mandelbrot();
    }

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mMandelbrot.draw();
    }

    public void onSurfaceChanged(GL10 unused, int _width, int _height) {
        Log.i("MyGLRenderer", _width + ":" + _height);
        onResized();
        GLES20.glViewport(0, 0, _width, _height);
        mMandelbrot.setViewport(new float[]{_width, _height});
    }

    void onResized() {
        float rangeR = mMandelbrot.bounds[3] - mMandelbrot.bounds[2];
        mMandelbrot.bounds[3] = (float)((mMandelbrot.bounds[1] - mMandelbrot.bounds[0]) * (width / height) / 1.4 + mMandelbrot.bounds[2]);
        float newRangeR = mMandelbrot.bounds[3] - mMandelbrot.bounds[2];
        mMandelbrot.bounds[2] -= (newRangeR - rangeR) / 2;
        mMandelbrot.bounds[3] = (float)((mMandelbrot.bounds[1] - mMandelbrot.bounds[0]) * (width / height) / 1.4 + mMandelbrot.bounds[2]);
    }

    void zoom(float rangeModifier) {
        float rangeI = mMandelbrot.bounds[1] - mMandelbrot.bounds[0];
        float newRangeI;
        newRangeI = rangeI * rangeModifier;
        float delta = newRangeI - rangeI;
        mMandelbrot.bounds[0] -= delta / 2;
        mMandelbrot.bounds[1] = mMandelbrot.bounds[0] + newRangeI;
        Log.i("MEME", width+":"+height);
        onResized();
    }

    static int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    static String readShader(String filename) {
        BufferedReader reader = null;
        String shader = "";
        try {
            reader = new BufferedReader(new InputStreamReader(assetManager.open(filename)));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                shader += mLine;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return shader;
    }

    static class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private final String TAG = "MyGestureListener";
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            Log.i(TAG, "onScroll: deltaX=" + String.valueOf(e2.getX() - e1.getX()) + ", deltaY=" + String.valueOf(e2.getY() - e1.getY()));
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
            Log.i(TAG, "onSingleTapUp: X=" + String.valueOf(e.getX()) + ", Y=" + String.valueOf(e.getY()));
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e)
        {
            Log.i(TAG, "onLongPress: X=" + String.valueOf(e.getX()) + ", Y=" + String.valueOf(e.getY()));
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
}