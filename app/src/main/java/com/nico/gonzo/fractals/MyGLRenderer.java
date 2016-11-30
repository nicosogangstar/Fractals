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

import static android.R.attr.width;
import static android.R.attr.height;


class MyGLRenderer implements GLSurfaceView.Renderer {

    private static Fractal mFractal;
    private static AssetManager assetManager;
    private static int dWidth, dHeight;

    MyGLRenderer(Context context) {
        assetManager = context.getAssets();
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mFractal = new Fractal("mandelbrot");
    }

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mFractal.draw();
    }

    public void onSurfaceChanged(GL10 unused, int _width, int _height) {
        dWidth = _width;
        dHeight = _height;
        Log.i("MyGLRenderer", _width + ":" + _height);
        onResized();
        GLES20.glViewport(0, 0, _width, _height);
        mFractal.setViewport(new float[]{_width, _height});
    }

    private static void onResized() {
        float rangeR = mFractal.bounds[3] - mFractal.bounds[2];
        mFractal.bounds[3] = (float)((mFractal.bounds[1] - mFractal.bounds[0]) * (width / height) / 1.4 + mFractal.bounds[2]);
        float newRangeR = mFractal.bounds[3] - mFractal.bounds[2];
        mFractal.bounds[2] -= (newRangeR - rangeR) / 2;
        mFractal.bounds[3] = (float)((mFractal.bounds[1] - mFractal.bounds[0]) * (width / height) / 1.4 + mFractal.bounds[2]);
    }

    static void zoom(float rangeModifier) {
        float rangeI = mFractal.bounds[1] - mFractal.bounds[0];
        float newRangeI;
        newRangeI = rangeI / rangeModifier;
        float delta = newRangeI - rangeI;
        mFractal.bounds[0] -= delta / 2;
        mFractal.bounds[1] = mFractal.bounds[0] + newRangeI;
        onResized();
    }

    static void pan(float distI, float distR) {
        float rangeI = mFractal.bounds[1] - mFractal.bounds[0];
        float rangeR = mFractal.bounds[3] - mFractal.bounds[2];

        float deltaI = (distR / dHeight) * rangeI;
        float deltaR = (distI / dWidth) * rangeR;

        mFractal.bounds[0] -= deltaI;
        mFractal.bounds[1] -= deltaI;
        mFractal.bounds[2] += deltaR;
        mFractal.bounds[3] += deltaR;
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

    static void increaseD(float amount) {
        mFractal.d += amount;
    }
}