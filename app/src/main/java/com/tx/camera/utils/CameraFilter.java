package com.tx.camera.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.opengl.GLES20;

import com.tx.camera.R;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static com.tx.camera.utils.OpenGLUtils.TEXTURE;
import static com.tx.camera.utils.OpenGLUtils.VERTEX;

/**
 * create by xu.tian
 *
 * @date 2021/4/7
 */
public class CameraFilter {

    private int vPosition ;
    private int vCoord;
    private int vTexture;
    private int vMatrix;
    private int program;
    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;

    public CameraFilter(Context context) {
        String vertexSharder = null;
        try {
            vertexSharder = OpenGLUtils.readRawTextFile(context, R.raw.camera_vert);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fragSharder = null;
        try {
            fragSharder = OpenGLUtils.readRawTextFile(context,R.raw.camera_frag);
        } catch (IOException e) {
            e.printStackTrace();
        }

        program = OpenGLUtils.createProgram(vertexSharder,fragSharder);

        GLES20.glUseProgram(program);
        vPosition = GLES20.glGetAttribLocation(program,"vPosition");
        vCoord = GLES20.glGetAttribLocation(program,"vCoord");
        vTexture = GLES20.glGetAttribLocation(program,"vTexture");
        vMatrix = GLES20.glGetAttribLocation(program,"vMatrix");

        vertexBuffer = ByteBuffer.allocateDirect(VERTEX.length*4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexBuffer.clear();
        vertexBuffer.put(VERTEX);

        textureBuffer = ByteBuffer.allocateDirect(TEXTURE.length*4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        textureBuffer.clear();
        textureBuffer.put(TEXTURE);
    }

    public void onDraw(float[] mtx , int texture){
        // 顶点坐标
        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(vPosition,2,GLES20.GL_FLOAT,false,0,vertexBuffer);
        GLES20.glEnableVertexAttribArray(vPosition);
        // 纹理坐标 ，贴图上颜色
        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(vCoord,2,GLES20.GL_FLOAT,false,0,textureBuffer);
        GLES20.glEnableVertexAttribArray(vCoord);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texture);
        GLES20.glUniform1i(vTexture,0);

        GLES20.glUniformMatrix4fv(vMatrix,1,false,mtx,0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texture);
    }


    public void release(){
        GLES20.glDeleteProgram(program);
    }

}
