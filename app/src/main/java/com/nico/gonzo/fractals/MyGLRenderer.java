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

class MyGLRenderer implements GLSurfaceView.Renderer {

    // Define fractals
    static Mandelbrot mMandelbrot;
    private static Julia mJulia;

    private static AssetManager assetManager;
    private final int fractalType;

    MyGLRenderer(Context context, int fractalType) {
        assetManager = context.getAssets();
        this.fractalType = fractalType;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        float[] topHalf = {
                -1, 1,
                -1, 0,
                1, 0,

                -1, 1,
                1, 1,
                1, 0
        };

        float[] bottomHalf = {
                -1, 0,
                -1, -1,
                1, -1,

                -1, 0,
                1, 0,
                1, -1
        };

        float[] full = {
                -1,  1,
                -1, -1,
                1, -1,

                -1,  1,
                1,  1,
                1, -1
        };

        float[] nill = {0, 0, 0, 0, 0, 0};

        switch (fractalType) {
            case 0:
                mMandelbrot = new Mandelbrot("mandelbrot", 400, full);
                mJulia = new Julia("julia", 0, nill);
                break;
            case 1:
                mMandelbrot = new Mandelbrot("mandelbrot", 100, topHalf);
                mJulia = new Julia("julia", 350, bottomHalf);
                break;
        }
    }

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mMandelbrot.draw();
        mJulia.setJuliaPos(mMandelbrot.getPosition());
        mJulia.draw();
    }

    public void onSurfaceChanged(GL10 unused, int _width, int _height) {
        Log.i("MyGLRenderer", _width + ":" + _height);
        GLES20.glViewport(0, 0, _width, _height);

        // Update fractals
        mMandelbrot.onResized();
        mMandelbrot.setViewport(new float[]{_width, _height});
        mJulia.setViewport(new float[]{_width, _height/2});
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
                if(mLine.contains("//"))
                    mLine = mLine.substring(0, mLine.indexOf("//"));
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