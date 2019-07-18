package com.example.lastdefence.impleClass;

import android.graphics.Canvas;

/**
 * Created by JiangYu on 2019-07-17 10:38
 */
public interface Bullet {
    public void drawSelf(Canvas canvas);

    public void run();

    public boolean isLive();

}
