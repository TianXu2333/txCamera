package com.tx.camera.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * create by xu.tian
 *
 * @date 2021/4/7
 */

public class MyGLSurfaceView extends GLSurfaceView {

    private MyGLRender renderer ;

    public MyGLSurfaceView(Context context) {
        super(context);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        renderer = new MyGLRender(this);
        setRenderer(renderer);

//        GLSurfaceView.RENDERMODE_WHEN_DIRTY 手动刷新
//        GLSurfaceView.RENDERMODE_CONTINUOUSLY 每16ms自动刷新一次
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder){
        super.surfaceDestroyed(surfaceHolder);
        renderer.onSurfaceDestroyed();
    }
}
