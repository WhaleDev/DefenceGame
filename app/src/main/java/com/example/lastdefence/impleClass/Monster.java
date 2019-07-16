package com.example.lastdefence.impleClass;

import android.graphics.Canvas;

public interface Monster {
    public void draw(Canvas canvas); //绘制

    public void run(); //运动

    public boolean isLive();  //是否存活

    public void bloodLoss();

}
