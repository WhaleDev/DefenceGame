package com.example.lastdefence.impleClass;

import android.graphics.Canvas;

public interface Monster {
    public void draw(Canvas canvas); //绘制

    public void run(); //运动

    public boolean isMonstersLive();  //是否存活

    public boolean decreaseBlood(float damage);

    public float[] getCurrentPoint();

    public void setMonstersLive(boolean monstersLive);


}
