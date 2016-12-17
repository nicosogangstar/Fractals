package com.nico.gonzo.fractals;
import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class MyGLRenderer implements GLSurfaceView.Renderer {

    // Define fractals
    static Fractal fractal;

    private static AssetManager assetManager;
    private final int fractalType;

    MyGLRenderer(Context context, int fractalType) {
        assetManager = context.getAssets();
        this.fractalType = fractalType;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        float[] coords = {
                -1,  1,
                -1, -1,
                1, -1,

                -1,  1,
                1,  1,
                1, -1
        };

        switch (fractalType) {
            case 0:
                fractal = new Mandelbrot(100, coords);
                break;
            case 1:
                fractal = new Julia(100, coords);
                break;
            case 2:
                fractal = new Newton(100, coords);
                break;
        }
    }

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        fractal.draw();
    }

    public void onSurfaceChanged(GL10 unused, int _width, int _height) {
        Log.i("MyGLRenderer", _width + ":" + _height);
        GLES20.glViewport(0, 0, _width, _height);

        fractal.onResized();
        fractal.setViewport(new float[]{_width, _height});
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
                // Process line
                if(mLine.contains("//"))
                    mLine = mLine.substring(0, mLine.indexOf("//"));
                else if(mLine.contains("void main()")) {
                    // Load in coloring functions
                    String coloring = readShader("coloring.glsl");
                    Log.d("MyGLRenderer", coloring);
                    shader += coloring;
                }
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
}