package com.tx.camera.view;

import android.graphics.SurfaceTexture;
import android.media.tv.TvContract;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.tx.camera.utils.CameraFilter;
import com.tx.camera.utils.CameraUtils;

import java.io.IOException;
import java.util.Objects;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import androidx.camera.core.Preview;
import androidx.lifecycle.LifecycleOwner;

/**
 * create by xu.tian
 *
 * @date 2021/4/7
 */
public class MyGLRender implements GLSurfaceView.Renderer, Preview.OnPreviewOutputUpdateListener, SurfaceTexture.OnFrameAvailableListener {

    private MyGLSurfaceView myGLSurfaceView ;

    private SurfaceTexture mCameraTexture;

    private int textureId ;

    float[] mtx = new float[16];

    private CameraFilter cameraFilter ;

    public MyGLRender(MyGLSurfaceView myGLSurfaceView){
        this.myGLSurfaceView = myGLSurfaceView ;
        LifecycleOwner lifecycleOwner = (LifecycleOwner)myGLSurfaceView.getContext();
        CameraUtils.init(lifecycleOwner,this);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mCameraTexture.attachToGLContext(textureId);
        mCameraTexture.setOnFrameAvailableListener(this);

        GLES20.glClearColor(0,0,0,0);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        cameraFilter = new CameraFilter(myGLSurfaceView.getContext());

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    public void onSurfaceDestroyed(){
        cameraFilter.release();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mCameraTexture.updateTexImage();
        mCameraTexture.getTransformMatrix(mtx);
        cameraFilter.onDraw(mtx,textureId);

    }


    @Override
    public void onUpdated(Preview.PreviewOutput output) {
        mCameraTexture = output.getSurfaceTexture();
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        myGLSurfaceView.requestRender();
    }
}
