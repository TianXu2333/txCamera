package com.tx.camera.utils;

import android.util.Size;

import androidx.camera.core.CameraX;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.LifecycleOwner;

/**
 * create by xu.tian
 *
 * @date 2021/4/7
 */
public class CameraUtils {

    private static CameraX.LensFacing currentCamera = CameraX.LensFacing.BACK;


    public static void init(LifecycleOwner lifecycleOwner , Preview.OnPreviewOutputUpdateListener listener){
        CameraX.bindToLifecycle(lifecycleOwner,getPreview(listener));
    }

    public static Preview getPreview(Preview.OnPreviewOutputUpdateListener listener){
        PreviewConfig previewConfig = new PreviewConfig
                .Builder()
                .setTargetResolution(new Size(1080,2400))
                .setLensFacing(currentCamera)
                .build();
        Preview preview = new Preview(previewConfig);
        preview.setOnPreviewOutputUpdateListener(listener);
        return preview;
    }

}
