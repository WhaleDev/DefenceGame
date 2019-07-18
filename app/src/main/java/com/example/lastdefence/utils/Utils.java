package com.example.lastdefence.utils;
/*
* 封装一些方法供其他类调用
* */
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.view.View;

public class Utils {
    public static void hideBottomUIMenu(Activity context) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = context.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = context.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public  static Bitmap small(Bitmap bmp, float scale)    {
        int bmpWidth=bmp.getWidth();
        int bmpHeight=bmp.getHeight();
        /* 产生reSize后的Bitmap对象 */
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,
                bmpHeight,matrix,true);
        return resizeBmp;

    }
}
