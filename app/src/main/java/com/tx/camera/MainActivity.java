package com.tx.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
   /* static {
        System.loadLibrary("native-lib");
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
   /* public native String stringFromJNI();*/
}
